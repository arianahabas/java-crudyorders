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

@Transactional
@Service(value = "customerService")
public class CustomerServiceImplementation implements CustomerService{
  @Autowired
  CustomersRepository custrepos;

  //VALIDATE THE DATA!
  @Transactional
  @Override
  public Customer save(Customer customer) {
    Customer newCustomer = new Customer();

    //single value fields
    newCustomer.setCustname(customer.getCustname());
    newCustomer.setCustcity(customer.getCustcity());
    newCustomer.setWorkingarea(customer.getWorkingarea());
    newCustomer.setCustcountry(customer.getCustcountry());
    newCustomer.setGrade(customer.getGrade());
    newCustomer.setOpeningamt(customer.getOpeningamt());
    newCustomer.setReceiveamt(customer.getReceiveamt());
    newCustomer.setPaymentamt(customer.getPaymentamt());
    newCustomer.setOutstandingamt(customer.getOutstandingamt());
    newCustomer.setPhone(customer.getPhone());

    //collections

    return custrepos.save(customer);
  }

  @Transactional
  @Override
  public void delete(long custcode) {
    if (custrepos.findById(custcode)
            .isPresent()){
      custrepos.deleteById(custcode);
    } else{
      throw new EntityNotFoundException("Customer " + custcode + " NOT FOUND!");
    }
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
