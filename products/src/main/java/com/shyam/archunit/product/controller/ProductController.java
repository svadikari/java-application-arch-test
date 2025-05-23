package com.shyam.archunit.product.controller;

import com.shyam.archunit.product.config.ProductConfiguration;
import com.shyam.archunit.product.domain.Product;
import com.shyam.archunit.product.service.ProductService;
import java.util.SequencedCollection;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/products")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class ProductController {

  private ProductService productService;

  private ProductConfiguration productConfiguration;

  @GetMapping
  public SequencedCollection<Product> getAllProducts() {
    return productService.getAllProducts();
  }

  @PatchMapping
  public SequencedCollection<Product> upsertProduct(@RequestBody Product product) {
    if(product.getName() == null){
      product.setName(productConfiguration.getProductName());
    }
    return productService.upsertProduct(product);
  }
}
