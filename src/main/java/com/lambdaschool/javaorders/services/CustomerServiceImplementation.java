package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.repositories.CustomersRepository;
import com.lambdaschool.javaorders.views.OrderCounts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.List;

@Service(value = "customerService")
public class CustomerServiceImplementation implements CustomerService{
  @Autowired
  CustomersRepository custrepos;

  @Transactional
  @Override
  public Customer save(Customer customer) {
    return custrepos.save(customer);
  }

  @Override
  public List<Customer> findCustomerOrders() {
    List<Customer> list = new ArrayList<>();
    custrepos.findAll().iterator().forEachRemaining(list::add);
    return list;
  }

  @Override
  public Customer findCustomerById(long id) {
    return custrepos.findById(id)
            .orElseThrow(() -> new EntityNotFoundException("Customer " + id + " NOT FOUND!"));
  }

  @Override
  public List<Customer> findByCustomerName(String subname) {
    List<Customer> list = custrepos.findByCustnameContainingIgnoringCase(subname);
    return list;
  }

  @Override
  public List<OrderCounts> countOrdersByCustomer() {
    List<OrderCounts> list = custrepos.findOrderCounts();
    return list;
  }
}
