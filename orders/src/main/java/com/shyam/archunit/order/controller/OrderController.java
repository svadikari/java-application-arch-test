package com.shyam.archunit.order.controller;

import com.shyam.archunit.order.domain.Order;
import com.shyam.archunit.order.service.OrderService;
import java.util.SequencedCollection;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/orders")
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderController {

  private OrderService orderService;

  @PostMapping
  public Order createOrder(@RequestBody Order order) {
    return orderService.createOrder(order);
  }

  @GetMapping
  public SequencedCollection<Order> getAllOrders() {
    return orderService.getAllOrders();
  }

  @GetMapping("/{id}")
  public Order getOrderById(@PathVariable Long id) {
    return orderService.getOrderById(id);
  }
}
