package com.shyam.archunit.order.service;

import com.shyam.archunit.order.domain.Order;
import java.util.SequencedCollection;

public interface OrderService {

  Order createOrder(Order order);

  Order getOrderById(Long id);

  SequencedCollection<Order> getAllOrders();
}
