package ru.yandex.practicum.exception;

import org.springframework.http.HttpStatus;

import java.util.Set;
import java.util.UUID;

public class NotEnoughProductsException extends ErrorResponse {
    public NotEnoughProductsException(Set<UUID> productIds) {
        super(HttpStatus.BAD_REQUEST,
                "Товаров с ID " + productIds + ", находящихся в корзине, недостаточно на складе.");
    }

    public NotEnoughProductsException(UUID productId) {
        super(HttpStatus.BAD_REQUEST,
                "Товара с ID " + productId + ", находящегося в корзине, недостаточно на складе.");
    }
}