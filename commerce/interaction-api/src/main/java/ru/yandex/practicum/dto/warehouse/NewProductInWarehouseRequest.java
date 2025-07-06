package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record NewProductInWarehouseRequest(
        @NotNull
        UUID productId,

        Boolean fragile,

        @NotNull
        DimensionsDto dimensions,

        @NotNull
        @DecimalMin("1.0")
        Double weight
) {
}