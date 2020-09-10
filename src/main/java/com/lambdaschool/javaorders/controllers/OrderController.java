package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.services.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
public class OrderController {

  @Autowired
  OrdersService ordersService;

  // GET : http://localhost:2019/orders/order/52 -> Returns the order and its customer with the given order number
  @GetMapping(value = "/orders/order/{id}", produces = "application/json")
  public ResponseEntity<?> listOrdersById(@PathVariable int id){
    Order o = ordersService.findByOrderId(id);
    return new ResponseEntity<>(o, HttpStatus.OK);
  }
  // GET STRETCH -> http://localhost:2019/orders/advanceamount
  // POST : http://localhost:2019/orders/order -> adds a new order to an existing customer
    @PostMapping(value = "/orders/order", consumes = "application/json", produces = "application/json")
    public ResponseEntity<?> addNewOrder (@Valid @RequestBody Order newOrder){
      newOrder.setOrdnum(0);
      newOrder = ordersService.save(newOrder);
      HttpHeaders responseHeaders = new HttpHeaders();
      URI newOrderURI = ServletUriComponentsBuilder.fromCurrentRequest()
              .path("/{ordnum}")
              .buildAndExpand(newOrder.getOrdnum())
              .toUri();
      responseHeaders.setLocation(newOrderURI);
      return new ResponseEntity<>("None", responseHeaders, HttpStatus.CREATED);
    }

  // PUT : http://localhost:2019/orders/order/{ordernum} -> completely replaces the given order record

  // DELETE : http://localhost:2019/orders/order/{ordername} -> deletes the given order
    @DeleteMapping(value = "/orders/order/{ordernum}", produces = "application/json")
    public ResponseEntity<?> deleteByOrderId (@PathVariable long ordernum){
      ordersService.delete(ordernum);
      return new ResponseEntity<>(HttpStatus.OK);
    }

}
