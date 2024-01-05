package com.example.domain.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Product {

  private Long id;
  private String name;
  private BigDecimal price;

  public Product(Long id) {
    this.id = id;
  }

  public Product(String name, BigDecimal price) {
    this.name = name;
    this.price = price;
  }
}
