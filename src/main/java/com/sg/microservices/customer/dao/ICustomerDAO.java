package com.sg.microservices.customer.dao;

import java.util.List;

import org.springframework.stereotype.Component;

import com.sg.microservices.customer.entity.Customer;



@Component
public interface ICustomerDAO  {
	
	

	public List<Customer> getCustomerDetailsbyUserId(String username);

	public String  saveCustomerDetails(Customer customerObj);
	
	public List<Customer> getAllCustomers();
}
