package com.lambdaschool.javaorders.controllers;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.services.CustomerService;
import com.lambdaschool.javaorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/customers")
public class CustomerController {

  @Autowired
  CustomerService customerService;

  // GET: http://localhost:2019/customers/orders -> Returns all customers with their orders
  @GetMapping(value = "/orders", produces = "application/json")
  public ResponseEntity<?> listCustomerOrders (){
    List<Customer> myList = customerService.findCustomerOrders();
    return new ResponseEntity<>(myList, HttpStatus.OK);
  }

  // GET: http://localhost:2019/customers/customer/17 -> Returns the customer and their orders with the given customer id
  @GetMapping(value = "/customer/{id}", produces = "application/json")
  public ResponseEntity<?> listCustomerOrdersById (@PathVariable int id){
    Customer c = customerService.findCustomerById(id);
    return new ResponseEntity<>(c, HttpStatus.OK);
  }

  // GET: http://localhost:2019/customers/namelike/mes -> Returns all customers and their orders with a customer name containing the given substring
  // GET: http://localhost:2019/customers/namelike/cin
  @GetMapping(value = "/namelike/{subname}", produces = "application/json")
  public ResponseEntity<?> listCustomerOrdersBySubname (@PathVariable String subname){
    List<Customer> rtnList = customerService.findByCustomerName(subname);
    return new ResponseEntity<>(rtnList, HttpStatus.OK);
  }

  // GET: http://localhost:2019/customers/orders/count -> Using a custom query, return a list of all customers with the number of orders they have placed.
  @GetMapping(value = "/orders/count", produces = "application/json")
  public ResponseEntity<?> getOrderCounts(){
    List<OrderCounts> rtnList = customerService.countOrdersByCustomer();
    return new ResponseEntity<>(rtnList, HttpStatus.OK);
  }

  // DELETE : http://localhost:2019/customer/{custcode} -> Deletes the given customer including any associated orders
  @DeleteMapping(value = "/customer/{custcode}", produces = "application/json")
  public ResponseEntity<?> deleteCustomerById(@PathVariable long custcode){
    customerService.delete(custcode);
    return new ResponseEntity<>(HttpStatus.OK);
  }

  // POST : http://localhost:2019/customer -> Adds a new customer including any new orders
  @PostMapping(value = "/customer", consumes = "application/json", produces = "application/json")
  public ResponseEntity<?> addNewCustomer(@Valid
                                          @RequestBody Customer newCustomer){
    newCustomer = customerService.save(newCustomer);
    // Response Header -> Location Header = url to the new customer
    HttpHeaders responseHeaders = new HttpHeaders();
    URI newCustomerURI = ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{custcode}")
            .buildAndExpand(newCustomer.getCustcode())
            .toUri();
    responseHeaders.setLocation(newCustomerURI);
    return new ResponseEntity<>(newCustomer, responseHeaders, HttpStatus.CREATED);
  }

  // PUT : http://localhost:2019/customer/{custcode} -> completely replaces the customer record including associated orders with the provided data
  // PATCH : http://localhost:2019/customer/{custcode} -> updates customers with the new data. Only the new data is to be sent from the frontend client.
}
