package com.example.rest;

import com.example.domain.model.Order;
import com.example.port.IOrderPort;
import com.example.usecase.NewOrderUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/order")
public class OrderAct {

  private final IOrderPort orderPort;
  private final NewOrderUseCase newOrderUseCase;

  @GetMapping("queryOrder/{id}")
  public Order queryOrder(@PathVariable("id")Long id) {
    return orderPort.getOrder(id);
  }

  @PostMapping("newOrder/{productId}/{quantity}")
  public Long newOrder(@PathVariable("productId") Long productId, @PathVariable("quantity") int quantity) {
    return newOrderUseCase.create(productId, quantity);
  }

}
