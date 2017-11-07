package com.sg.microservices.customer.service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sg.microservices.customer.dao.ICustomerDAO;
import com.sg.microservices.customer.entity.Customer;




@Service
@Transactional
@Configuration
@Component
public class CustomerServiceImpl implements ICustomerService {

	private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceImpl.class);
	String customlogger ="ADMS Loger::::";
   

	List<Customer> customerList = null;

	@Autowired
	private ICustomerDAO customerdao;

	public CustomerServiceImpl(ICustomerDAO customerdao) {
		this.customerdao = customerdao;
	}

	
	@Override
	public Customer getCustomerDetails(String username) {
		LOGGER.info(customlogger+"CustomerService Started");
		LOGGER.info(customlogger+"Inside getCustomerDetails method");  
		Customer custObj =  null ;
		List<Customer> customerlist  = new ArrayList<Customer>() ;
 
		try {
			customerlist = customerdao.getCustomerDetailsbyUserId(username);
			if (customerlist != null && customerlist.size()>0) {
				LOGGER.info(customlogger+"Customer List Object ########"+customerlist + "and size is "+customerlist.size());
				for(int i = 0 ;i<customerlist.size(); i++)
				{
					custObj = customerlist.get(i);
				}
				
				
			}
		} catch (Exception e) {
			LOGGER.error("Exception Occured::"+e.getMessage());
			e.printStackTrace();
		}
		
		LOGGER.info("Resultant Customer Object ::"+custObj);
		return custObj;
	}

	@Override
	public String saveCustomerDetails(Customer customerObj) {
		LOGGER.info(customlogger+"CustomerService Started");
		LOGGER.info(customlogger+"inside saveCustomerDetails");
		String result =null;
		try {
			result = customerdao.saveCustomerDetails(customerObj);
			LOGGER.info(customlogger+"Result of customerDetails saved mehtod:::"+result);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return result;
	}

	@Override
	public List<Customer> getAllCustomers() {
		LOGGER.info(customlogger+"Inside getAllCustomers()");
		try {
			customerList = customerdao.getAllCustomers();
			if(customerList.size()>0)
			LOGGER.info(customlogger+"customer List ::"+customerList);
		} catch (Exception e) {
			e.printStackTrace();
		}

		return customerList;

	}

}
