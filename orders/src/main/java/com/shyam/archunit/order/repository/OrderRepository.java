package com.shyam.archunit.order.repository;

import com.shyam.archunit.order.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {

  Order getOrderById(Long id);
}
