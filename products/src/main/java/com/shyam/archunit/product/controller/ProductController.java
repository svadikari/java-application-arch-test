package com.shyam.archunit.product.controller;

import com.shyam.archunit.product.domain.Product;
import com.shyam.archunit.product.service.ProductService;
import java.util.SequencedCollection;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

  private ProductService productService;

  @GetMapping
  public SequencedCollection<Product> getAllProducts() {
    return productService.getAllProducts();
  }
}
