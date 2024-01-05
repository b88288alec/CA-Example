package com.example.domain.model;

import java.math.BigDecimal;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Order {

  private Long id;
  private int quantity;
  private BigDecimal amount;
  private Long productId;

  public Order(Long id) {
    this.id = id;
  }

  public Order(Long productId, int quantity) {
    this.productId = productId;
    this.quantity = quantity;
  }

  public void calculateAmount(BigDecimal price) {
    this.amount = price.multiply(BigDecimal.valueOf(quantity));
  }

}
