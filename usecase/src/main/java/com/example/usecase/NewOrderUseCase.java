package com.example.usecase;

import com.example.domain.model.Order;
import com.example.domain.model.Product;
import com.example.domain.service.StockService;
import com.example.exception.OutOfStockException;
import com.example.port.IOrderPort;
import com.example.port.IProductPort;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NewOrderUseCase {

  private final IOrderPort orderPort;
  private final IProductPort productPort;
  private final StockService stockService;

  public Long create(Long productId, int quantity) {
    Product product = productPort.getProduct(productId);
    // 檢查產品是否還有庫存
    boolean isPossibleOrder = stockService.isPossibleOrder(productId, quantity);
    if (isPossibleOrder) {
      // 訂單成立
      Order order = new Order(productId, quantity);
      order.calculateAmount(product.getPrice());
      Long orderId = orderPort.saveNewOrder(order);
      // 扣除庫存數量
      stockService.decrease(productId, quantity);
      return orderId;
    } else {
      // 沒庫存拋錯誤
      throw new OutOfStockException("The product id " + productId + " is out of stock");
    }
  }

}
