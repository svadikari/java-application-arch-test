package com.shyam.archunit.product.service;

import com.shyam.archunit.product.domain.Product;
import com.shyam.archunit.product.repository.ProductRepository;
import java.util.SequencedCollection;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductServiceImpl implements ProductService {

  private ProductRepository productRepository;

  @Override
  public SequencedCollection<Product> getAllProducts() {
    return productRepository.findAll();
  }
}
