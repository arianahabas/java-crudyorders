package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.views.OrderCounts;

import java.util.List;

public interface CustomerService {

  //things the customer controller can do/access is controlled by services
  Customer save (Customer customer);

  List<Customer> findCustomerOrders();

  Customer findCustomerById(long id);

  List<Customer> findByCustomerName(String subname);

  List<OrderCounts> countOrdersByCustomer();
}
