package com.sg.microservices.customer.service;

import java.util.List;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import com.sg.microservices.customer.entity.Customer;



@Service
@Component
public interface ICustomerService {
	
		public Customer getCustomerDetails(String username);

		public String saveCustomerDetails(Customer customerObj);
		
		public List<Customer> getAllCustomers();

	 
}
