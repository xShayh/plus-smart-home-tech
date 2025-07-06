package ru.yandex.practicum.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Builder;

import java.util.UUID;

@Builder
public record ChangeProductQuantityRequest(
        @NotNull
        UUID productId,

        @NotNull
        @PositiveOrZero
        Long newQuantity
) {
}