package com.shyam.archunit.product.service;

import com.shyam.archunit.product.domain.Product;
import java.util.SequencedCollection;

public interface ProductService {
  SequencedCollection<Product> getAllProducts();
}
