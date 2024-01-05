package com.example.port;

import com.example.domain.model.Order;

public interface IOrderPort {

  Order getOrder(Long id);

  Long saveNewOrder(Order order);

}
