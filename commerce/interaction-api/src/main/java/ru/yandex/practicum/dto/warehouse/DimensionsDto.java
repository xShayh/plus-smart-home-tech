package ru.yandex.practicum.dto.warehouse;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

@Builder
public record DimensionsDto(
        @NotNull
        @DecimalMin("1.0")
        Double width,

        @NotNull
        @DecimalMin("1.0")
        Double height,

        @NotNull
        @DecimalMin("1.0")
        Double depth
) {
}