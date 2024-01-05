package com.example.db.adapter;

import com.example.db.entity.OrderEntity;
import com.example.db.repository.OrderRepository;
import com.example.domain.model.Order;
import com.example.port.IOrderPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class OrderPort implements IOrderPort {

  private final OrderRepository orderRepository;

  @Override
  public Order getOrder(Long id) {
    Optional<OrderEntity> orderOptional = orderRepository.findById(id);
    return orderOptional.map(orderEntityEntity -> {
      Order order = new Order();
      order.setId(orderEntityEntity.getId());
      order.setProductId(orderEntityEntity.getProductId());
      order.setQuantity(orderEntityEntity.getQuantity());
      order.setAmount(orderEntityEntity.getAmount());
      return order;
    }).orElse(null);
  }

  @Override
  public Long saveNewOrder(Order order) {
    OrderEntity orderEntity = new OrderEntity();
    orderEntity.setProductId(order.getProductId());
    orderEntity.setQuantity(order.getQuantity());
    orderEntity.setAmount(order.getAmount());
    OrderEntity saved = orderRepository.save(orderEntity);
    return saved.getId();
  }
}
