package ru.yandex.practicum.dto.product;

public enum QuantityState {
    ENDED,  // Товар закончился
    FEW,    // Осталось меньше 10 единиц товара
    ENOUGH, // Осталось от 10 до 100 единиц
    MANY    // Осталось больше 100 единиц
}