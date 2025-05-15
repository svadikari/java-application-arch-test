package com.shyam.archunit.product.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Configuration
@Data
public class ProductConfiguration {

  @Value(("${product.name}"))
  private String productName;

}
