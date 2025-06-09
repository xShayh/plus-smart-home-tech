package ru.yandex.practicum;

import lombok.extern.slf4j.Slf4j;
import org.apache.avro.specific.SpecificRecordBase;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

import org.apache.kafka.common.errors.WakeupException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import ru.yandex.practicum.config.KafkaConsumerService;
import ru.yandex.practicum.config.KafkaProducerService;
import ru.yandex.practicum.kafka.telemetry.event.SensorEventAvro;
import ru.yandex.practicum.kafka.telemetry.event.SensorsSnapshotAvro;

import java.time.Duration;
import java.util.List;
import java.util.Optional;

@Slf4j
@Component
public class AggregationStarter {
    private final KafkaConsumerService consumerService;
    private final KafkaProducerService producerService;
    private final SnapshotStorage snapshotStorage;
    private final String inputTopic;
    private final String outputTopic;

    public AggregationStarter(
            KafkaConsumerService consumerService,
            KafkaProducerService producerService,
            SnapshotStorage snapshotStorage,
            @Value("${kafka.input-topic}") String inputTopic,
            @Value("${kafka.output-topic}") String outputTopic) {
        this.consumerService = consumerService;
        this.producerService = producerService;
        this.snapshotStorage = snapshotStorage;
        this.inputTopic = inputTopic;
        this.outputTopic = outputTopic;
    }

    public void start() {
        try {
            consumerService.subscribe(List.of(inputTopic));
            log.info("Подписка на топик {}", inputTopic);

            while (true) {
                ConsumerRecords<String, SpecificRecordBase> records = consumerService.poll(Duration.ofMillis(1000));

                for (ConsumerRecord<String, SpecificRecordBase> record : records) {
                    try {
                        SensorEventAvro event = (SensorEventAvro) record.value();
                        Optional<SensorsSnapshotAvro> snapshotOpt = snapshotStorage.updateState(event);

                        snapshotOpt.ifPresent(snapshot -> {
                            try {
                                producerService.send(outputTopic, snapshot.getHubId(), snapshot);
                                log.info("Сообщение отправлено в Kafka: топик={}, ключ={}", outputTopic, snapshot.getHubId());
                            } catch (Exception e) {
                                log.error("Ошибка при отправке сообщения в Kafka", e);
                            }
                        });

                    } catch (Exception e) {
                        log.error("Ошибка при обработке записи: ключ={}, значение={}", record.key(), record.value(), e);
                    }
                }

                consumerService.commitSync();
            }
        } catch (WakeupException ignored) {
            log.error("Получен WakeupException");
        } catch (Exception e) {
            log.error("Ошибка во время обработки событий от датчиков", e);
        } finally {
            try {
                producerService.flush();
                log.info("Все данные отправлены в Kafka");
                consumerService.commitSync();
                log.info("Смещения зафиксированы");
            } finally {
                log.info("Закрытие консьюмера");
                consumerService.close();
                log.info("Закрытие продюсера");
                producerService.close();
            }
        }
    }
}

