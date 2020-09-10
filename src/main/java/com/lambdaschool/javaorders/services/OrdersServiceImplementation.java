package com.lambdaschool.javaorders.services;

import com.lambdaschool.javaorders.models.Customer;
import com.lambdaschool.javaorders.models.Order;
import com.lambdaschool.javaorders.models.Payment;
import com.lambdaschool.javaorders.repositories.CustomersRepository;
import com.lambdaschool.javaorders.repositories.OrdersRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityNotFoundException;

@Service(value ="ordersService")
public class OrdersServiceImplementation implements OrdersService {

  @Autowired
  CustomersRepository custrepos;

  @Autowired
  OrdersRepository ordersrepos;

  @Transactional
  @Override
  public Order save(Order order){
    Order newOrder = new Order();
    if (order.getOrdnum() > 0){
      findByOrderId(order.getOrdnum());
      newOrder.setOrdnum(order.getOrdnum());
    }
    //single value fields
    newOrder.setOrdamount(order.getOrdamount());
    newOrder.setAdvanceamount(order.setAdvanceamount());
    newOrder.setOrderdescription(order.getOrderdescription());

    //collections (lists or sets)


    return ordersrepos.save(newOrder);
  }

  @Transactional
  @Override
  public void delete(long ordernum) {
    if (ordersrepos.findById(ordernum).isPresent()){
      ordersrepos.deleteById(ordernum);
    }else{
      throw new EntityNotFoundException("Order " + ordernum + " NOT FOUND!");
    }
  }

  @Transactional


  @Override
  public Order findByOrderId(long id) {
    return ordersrepos.findById(id)
            .orElseThrow(()-> new EntityNotFoundException("Order " + id + " NOT FOUND"));
  }
}
