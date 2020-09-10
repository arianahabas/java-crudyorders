package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
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
  @Autowired
  OrdersService orderrepos;

  //VALIDATE THE DATA!
  @Transactional
  @Override

  public Customer save(Customer customer) {
    Customer newCustomer = new Customer();
    if (customer.getCustcode() != 0){
      findCustomerById(customer.getCustcode());
      newCustomer.setCustcode(customer.getCustcode());
    }

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

    //collections (lists or sets)
    newCustomer.getOrders().clear();
    for (Order o : customer.getOrders()){
      Order newOrder = new Order(o.getOrdamount(),o.getAdvanceamount(),newCustomer,o.getOrderdescription());
      newCustomer.getOrders().add(newOrder);
    }
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

  @Transactional
  @Override
  public Customer update(Customer customer, long custcode) {

    Customer updateCustomer = findCustomerById(custcode);

    //single value fields
    if (customer.getCustname() != null) {
      updateCustomer.setCustname(customer.getCustname());
    }
    if (customer.getCustcity() != null) {
      updateCustomer.setCustcity(customer.getCustcity());
    }
    if (customer.getWorkingarea() != null) {
      updateCustomer.setWorkingarea(customer.getWorkingarea());
    }
    if (customer.getCustcountry() != null) {
      updateCustomer.setCustcountry(customer.getCustcountry());
    }
    if (customer.getGrade() != null) {
      updateCustomer.setGrade(customer.getGrade());
    }
    if (customer.hasvalueforopeningamt) {
      updateCustomer.setOpeningamt(customer.getOpeningamt());
    }
    if (customer.hasvalueforreceiveamt) {
      updateCustomer.setReceiveamt(customer.getReceiveamt());
    }
    if (customer.hasvalueforpaymentamt) {
      updateCustomer.setPaymentamt(customer.getPaymentamt());
    }
    if (customer.hasvalueforoutstandingamt) {
      updateCustomer.setOutstandingamt(customer.getOutstandingamt());
    }
    if (customer.getPhone() != null) {
      updateCustomer.setPhone(customer.getPhone());
    }

    //collections (lists or sets)
    if (customer.getOrders().size() > 0) {

      updateCustomer.getOrders().clear();
      for (Order o : customer.getOrders()) {
        Order newOrder = new Order(o.getOrdamount(), o.getAdvanceamount(), updateCustomer, o.getOrderdescription());
        updateCustomer.getOrders().add(newOrder);
      }
    }
      return custrepos.save(updateCustomer);
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
