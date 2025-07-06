package ru.yandex.practicum.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;

import java.math.BigDecimal;
import java.util.UUID;

@Builder
public record ProductDto(
        UUID productId,

        @NotBlank
        String productName,

        @NotBlank
        String description,

        String imageSrc,

        @NotNull
        QuantityState quantityState,

        @NotNull
        ProductState productState,

        ProductCategory productCategory,

        @NotNull
        @DecimalMin(value = "1.0")
        BigDecimal price
) {
}