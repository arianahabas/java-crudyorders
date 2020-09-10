package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class OrderController {

  @Autowired
  OrdersService ordersService;

  //http://localhost:2019/orders/order/52 -> Returns the order and its customer with the given order number
  @GetMapping(value = "/orders/order/{id}", produces = "application/json")
  public ResponseEntity<?> listOrdersById(@PathVariable int id){
    Order o = ordersService.findByOrderId(id);
    return new ResponseEntity<>(o, HttpStatus.OK);
  }
  //STRETCH-> http://localhost:2019/orders/advanceamount


}
