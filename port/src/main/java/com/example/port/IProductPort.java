package com.example.port;

import com.example.domain.model.Product;

public interface IProductPort {

  Product getProduct(Long id);

  void saveNewProduct(Product product);

}
