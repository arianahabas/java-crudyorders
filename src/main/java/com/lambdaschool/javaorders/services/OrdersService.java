package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;

public interface OrdersService {
  Order save (Order order);

  Order findByOrderId(long id);
}
