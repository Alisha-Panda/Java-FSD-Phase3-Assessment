package com.dell.webservice.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import com.dell.webservice.entity.Order;
import com.dell.webservice.entity.Product;
import com.dell.webservice.interfaces.OrderRepository;
import com.dell.webservice.interfaces.ProductRepository;

@Service
public class OrderService {
	@Autowired
	OrderRepository orderRepository;
	
	public List<Order> getEntityOrders(Integer pageNo, Integer pageSize, String sortBy) throws Exception{
		try {
			Pageable paging = PageRequest.of(pageNo, pageSize,Sort.by(sortBy));
			Page<Order> pagedResult =  orderRepository.findAll(paging);
			if(pagedResult.hasContent()) {
	            return pagedResult.getContent();
	        } else {
	            return new ArrayList<Order>();
	        }
		}
		catch(Exception ex) {
			throw new Exception("Unable to retrieve orders "+ex.getMessage().toString());
		}
	}
	public Optional<Order> getEntityOrder(int orderId) throws Exception{
		try {
			return this.orderRepository.findById(orderId);
		}
		catch(Exception ex){
			throw new Exception("Unable to retrieve order with id"+orderId+" "+ex.getMessage().toString());
		}
	}
	public void addEntityOrder(Order addOrder) throws Exception {
		try {
			double total = 0.0;
			for( Product prod : addOrder.getProducts()) {
				System.out.print(prod.getPrice());
				total = total+ prod.getPrice();
			}
			System.out.println(total);
			addOrder.setTotalPrice(total);
			this.orderRepository.save(addOrder);
		}
		catch(Exception ex) {
			throw new Exception("Unable to add order "+ex.getMessage().toString());
		}
	}
	
	public void updateEntityOrder(Order updateOrder) throws Exception {
		try {
			this.orderRepository.save(updateOrder);
		}
		catch(Exception ex) {
			throw new Exception("Unable to update order "+ex.getMessage().toString());
		}
	}
	
	public void deleteEntityOrder(int orderId) throws Exception {
		try {
			this.orderRepository.deleteById(orderId);
		}
		catch(Exception ex) {
			throw new Exception("Unable to delete order "+ex.getMessage().toString());
		}
	}
}
