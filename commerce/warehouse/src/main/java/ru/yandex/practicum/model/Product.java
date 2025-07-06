package ru.yandex.practicum.model;

import jakarta.persistence.*;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

import java.util.UUID;

@Entity
@Table(name = "warehouse_product")
@EqualsAndHashCode(of = "id")
@Getter
@Setter
@ToString
public class Product {
    @Id
    private UUID id;

    @Column(name = "fragile")
    private Boolean fragile;

    @Embedded
    private Dimension dimension;

    @Column(name = "weight")
    private double weight;

    @Column(name = "quantity", nullable = false)
    private long quantity;
}