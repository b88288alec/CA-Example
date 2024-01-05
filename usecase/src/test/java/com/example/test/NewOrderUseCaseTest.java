package com.example.test;

import com.example.domain.model.Product;
import com.example.domain.service.StockService;
import com.example.exception.OutOfStockException;
import com.example.port.IOrderPort;
import com.example.port.IProductPort;
import com.example.usecase.NewOrderUseCase;
import java.math.BigDecimal;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class)
@SpringBootTest(classes = { NewOrderUseCase.class, StockService.class })
class NewOrderUseCaseTest {

  @MockBean
  private IOrderPort orderPort;
  @MockBean
  private IProductPort productPort;
  @MockBean
  private StockService stockService;
  @Autowired
  private NewOrderUseCase newOrderUseCase;

  @Test
  @DisplayName("下新訂單(有庫存)")
  void createHasStock() {
    this.mock_isPossibleOrder(true);
    this.mock_getProduct(new Product(20L, "test", new BigDecimal(100)));

    Product product = new Product(20L);
    newOrderUseCase.create(product.getId(), 10);

    // 驗證真的有存到資料庫
    Mockito.verify(orderPort, Mockito.times(1)).saveNewOrder(Mockito.any());
    // 驗證真的有減少庫存量
    Mockito.verify(stockService, Mockito.times(1)).decrease(product.getId(), 10);
  }

  @Test
  @DisplayName("下新訂單(沒庫存)")
  void createOutOfStock() {
    this.mock_isPossibleOrder(false);

    Product product = new Product(20L);
    try {
      newOrderUseCase.create(product.getId(), 10);
      Assertions.fail("An error should be thrown");
    } catch (OutOfStockException e) {
         /* 注意：catch 裡不能放 Exception，因為程式預期會為拋出的錯只有 OutOfStockException，
         如果拋出了非 OutOfStockException 的例外都屬非預期的行為，應該要讓測試失敗 */
      Assertions.assertThat(e).hasMessageContaining("out of stock");
    }
  }

  private void mock_isPossibleOrder(boolean isPossibleOrder) {
    Mockito.when(stockService.isPossibleOrder(Mockito.anyLong(), Mockito.anyInt()))
        .thenReturn(isPossibleOrder);
  }

  private void mock_getProduct(Product product) {
    Mockito.when(productPort.getProduct(Mockito.anyLong()))
        .thenReturn(product);
  }

}
