package com.shyam.archunit.product.service;

import com.shyam.archunit.product.domain.Product;
import com.shyam.archunit.product.repository.ProductRepository;
import java.util.SequencedCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ProductServiceImpl implements ProductService {

  @Autowired
  public ProductRepository productRepository;

  @Override
  public SequencedCollection<Product> getAllProducts() {
    return productRepository.findAll();
  }

  @Override
  public SequencedCollection<Product> upsertProduct(Product product) {
    return null;
  }
}
