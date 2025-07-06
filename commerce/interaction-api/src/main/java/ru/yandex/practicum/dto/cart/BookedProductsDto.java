package ru.yandex.practicum.dto.cart;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;

@Builder
public record BookedProductsDto(
        @NotNull
        @Positive
        Double deliveryWeight,

        @NotNull
        @Positive
        Double deliveryVolume,

        @NotNull
        Boolean fragile
) {
}