package com.example.db.adapter;

import com.example.db.entity.ProductEntity;
import com.example.db.repository.ProductRepository;
import com.example.domain.model.Product;
import com.example.port.IProductPort;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class ProductPort implements IProductPort {

  private final ProductRepository productRepository;

  @Override
  public Product getProduct(Long id) {
    Optional<ProductEntity> productOptional = productRepository.findById(id);
    return productOptional.map(productEntityEntity -> {
      Product product = new Product();
      product.setId(productEntityEntity.getId());
      product.setName(productEntityEntity.getName());
      product.setPrice(productEntityEntity.getPrice());
      return product;
    }).orElse(null);
  }

  @Override
  public void saveNewProduct(Product product) {
    ProductEntity productEntity = new ProductEntity();
    productEntity.setName(product.getName());
    productEntity.setPrice(product.getPrice());
    productRepository.save(productEntity);
  }
}
