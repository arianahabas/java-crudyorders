package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.services.CustomerService;
import com.lambdaschool.javaorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  CustomerService customerService;

  //http://localhost:2019/customers/orders -> Returns all customers with their orders
  @GetMapping(value = "/orders", produces = "application/json")
  public ResponseEntity<?> listCustomerOrders (){
    List<Customer> myList = customerService.findCustomerOrders();
    return new ResponseEntity<>(myList, HttpStatus.OK);
  }

  //http://localhost:2019/customers/customer/17 -> Returns the customer and their orders with the given customer id
  @GetMapping(value = "/customer/{id}", produces = "application/json")
  public ResponseEntity<?> listCustomerOrdersById (@PathVariable int id){
    Customer c = customerService.findCustomerById(id);
    return new ResponseEntity<>(c, HttpStatus.OK);
  }

  //http://localhost:2019/customers/namelike/mes -> Returns all customers and their orders with a customer name containing the given substring
  //http://localhost:2019/customers/namelike/cin
  @GetMapping(value = "/namelike/{subname}", produces = "application/json")
  public ResponseEntity<?> listCustomerOrdersBySubname (@PathVariable String subname){
    List<Customer> rtnList = customerService.findByCustomerName(subname);
    return new ResponseEntity<>(rtnList, HttpStatus.OK);
  }

  //http://localhost:2019/customers/orders/count -> Using a custom query, return a list of all customers with the number of orders they have placed.
  @GetMapping(value = "/orders/count", produces = "application/json")
  public ResponseEntity<?> getOrderCounts(){
    List<OrderCounts> rtnList = customerService.countOrdersByCustomer();
    return new ResponseEntity<>(rtnList, HttpStatus.OK);
  }
}
