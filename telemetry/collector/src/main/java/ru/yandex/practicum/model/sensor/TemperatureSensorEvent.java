package ru.yandex.practicum.model.sensor;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import ru.yandex.practicum.model.sensor.type.SensorEventType;

@Getter
@Setter
@ToString(callSuper = true)
public class TemperatureSensorEvent extends SensorEvent {
    @NotNull
    private Integer temperatureC;

    @NotNull
    private Integer temperatureF;

    @Override
    public SensorEventType getType() {
        return SensorEventType.TEMPERATURE_SENSOR_EVENT;
    }
}