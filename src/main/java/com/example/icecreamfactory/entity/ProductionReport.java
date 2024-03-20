package com.example.icecreamfactory.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProductionReport {
    private Long production_quantity;
    private String product;
    private BigDecimal production_cost;
    private BigDecimal selling_price;
    private BigDecimal cost_per_product;
    private BigDecimal profite;
    private LocalDate production_date;

    // Getters and setters
}
