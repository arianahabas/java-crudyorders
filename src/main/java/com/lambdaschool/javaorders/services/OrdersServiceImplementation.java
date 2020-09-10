package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service(value ="ordersService")
public class OrdersServiceImplementation implements OrdersService {

  @Autowired
  OrdersRepository ordersrepos;

  @Transactional
  @Override
  public Order save(Order order){
    return ordersrepos.save(order);
  }

//  @Override
//  public void delete(String ordername) {
//
//  }

  @Transactional


  @Override
  public Order findByOrderId(long id) {
    return ordersrepos.findById(id)
            .orElseThrow(()-> new EntityNotFoundException("Order " + id + " NOT FOUND"));
  }
}
