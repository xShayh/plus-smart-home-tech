package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.util.UUID;

@Builder
public record AddProductToWarehouseRequest(
        @NotNull
        UUID productId,

        @NotNull
        @Min(1)
        Long quantity
) {
}