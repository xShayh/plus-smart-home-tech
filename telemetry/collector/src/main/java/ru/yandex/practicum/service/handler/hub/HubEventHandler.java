package ru.yandex.practicum.service.handler.hub;

import ru.yandex.practicum.model.hub.HubEvent;
import ru.yandex.practicum.model.hub.type.HubEventType;

public interface HubEventHandler {
    HubEventType getMessageType();

    void handle(HubEvent event);
}
