package com.shyam.archunit.order.service;

import com.shyam.archunit.order.domain.Order;
import com.shyam.archunit.order.repository.OrderRepository;
import java.util.SequencedCollection;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor(onConstructor = @__(@Autowired))
public class OrderServiceImpl implements OrderService {

  private OrderRepository orderRepository;

  @Override
  public Order createOrder(Order order) {
    return orderRepository.save(order);
  }

  @Override
  public Order getOrderById(Long id) {
    return orderRepository.getOrderById(id);
  }

  @Override
  public SequencedCollection<Order> getAllOrders() {
    return orderRepository.findAll();
  }

}
