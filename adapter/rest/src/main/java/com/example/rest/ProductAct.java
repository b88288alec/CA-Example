package com.example.rest;

import com.example.domain.model.Product;
import com.example.port.IProductPort;
import java.math.BigDecimal;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductAct {

  private final IProductPort productPort;

  @PostMapping("newProduct/{name}/{price}")
  public void newProduct(@PathVariable("name") String name, @PathVariable("price") BigDecimal price) {
    productPort.saveNewProduct(new Product(name, price));
  }

}
