package com.example.icecreamfactory.dao;

import com.example.icecreamfactory.entity.Category;
import com.example.icecreamfactory.utility.Unit;
import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Data
public class ProductDto {
    private Long id;

    private String name;

    private int quantity;

    private BigDecimal price;

    private String unit;

    private String productType;

    private String createdDate;

    private String updatedDated;

    private List<Category> categories = new ArrayList<>();
}
