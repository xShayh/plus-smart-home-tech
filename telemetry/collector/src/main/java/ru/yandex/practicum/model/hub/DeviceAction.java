package ru.yandex.practicum.model.hub;

import lombok.Data;
import ru.yandex.practicum.model.hub.type.ActionType;

@Data
public class DeviceAction {
    private String sensorId;
    private ActionType type;
    private int value;
}