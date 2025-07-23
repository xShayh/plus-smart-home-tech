package ru.practicum.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.FieldDefaults;

import java.util.UUID;

@Data
@FieldDefaults(level = AccessLevel.PRIVATE)
@AllArgsConstructor
@NoArgsConstructor
public class NewProductInWarehouseRequest {
    @NotNull
    UUID productId;
    boolean fragile;
    @NotNull
    DimensionDto dimension;
    @NotNull
    @Min(value = 1)
    Double weight;
}
