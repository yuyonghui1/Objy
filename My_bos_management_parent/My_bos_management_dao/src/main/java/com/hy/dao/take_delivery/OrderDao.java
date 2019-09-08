package com.hy.dao.take_delivery;

import org.springframework.data.jpa.repository.JpaRepository;

import com.hy.domain.take_delivery.Order;

public interface OrderDao extends JpaRepository<Order, Integer>{

}
