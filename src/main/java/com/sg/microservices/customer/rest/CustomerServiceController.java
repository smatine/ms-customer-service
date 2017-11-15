package com.sg.microservices.customer.rest;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.Consumes;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import org.json.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.support.SpringBootServletInitializer;
import org.springframework.cloud.sleuth.Tracer;
import org.springframework.http.HttpHeaders;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixProperty;
import com.sg.microservices.customer.entity.Customer;
import com.sg.microservices.customer.service.ICustomerService;




@RestController
@RequestMapping("/customerService")
@Component
@CrossOrigin
public class CustomerServiceController   extends SpringBootServletInitializer {

private static final Logger LOGGER = LoggerFactory.getLogger(CustomerServiceController.class);

private static final Logger eslogger = LoggerFactory.getLogger("es-logger");

String customlogger ="ADMS Loger::::";

	@Autowired
	private ICustomerService customerService;
	
	@Autowired
	Tracer tracer;
	
    private static final String signingKey = "signingKey";
 	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	@CrossOrigin
	public @ResponseBody Customer authenticateCustomer(HttpServletResponse httpServletResponse, @RequestHeader HttpHeaders headers,@RequestBody String userCredentials) {

		LOGGER.info(customlogger+"userCredentials::" + userCredentials);
		JSONObject userCredjson = null;
		Customer customerObj = null;
		String username = null;
		Customer customerresultObj = null ;
		boolean validation =false;
		String jwttoken = null;
		LOGGER.info(customlogger+"Header Object in Customer Service::::::"+headers);
		if(headers !=null){
			List<String> token = headers.get("jwtToken");
			LOGGER.info("JWT token:::::"+token.get(0));
			 jwttoken = token.get(0); 
		}
		
		try{
			
			if (jwttoken != null) {
				validation = JwtUtil.validateJWT(jwttoken);
				if (validation) {
					String jwtUsername = JwtUtil.getSubject(jwttoken);
					// Get user details from Customer Service here.
					if (userCredentials != null)
						userCredjson = new JSONObject(userCredentials.toString());
					if (userCredjson != null)
						username = userCredjson.optString("username");
					if (username != null)
						customerObj = customerService.getCustomerDetails(username);

					// User Authentication
					if (username.equals(jwtUsername)) {
						LOGGER.info("JWT Token Validataion Sucessful in OrderService.");
						LOGGER.info("new Order Deatils saved Sucessfully");
						customerresultObj = customerObj;
						
					}
				}
			}
		}
		
		catch(Exception e){
			
			System.err.println("Exception Ocurred:"+e.getMessage());
		}
		
		return customerresultObj;
	}
	
	
	@RequestMapping(value="/test" , method = RequestMethod.GET)
	@CrossOrigin
	public String testService(){
		
		eslogger.info("Inside Customer service Test Method");
		
		eslogger.info("Correlation_id is "  +  tracer.getCurrentSpan().traceIdString());
		
		return "cutomer Service Sucess";
	}
	
	
	
	
	@Produces(MediaType.APPLICATION_JSON)
	@Consumes(MediaType.APPLICATION_JSON)
	@RequestMapping(value="/registerCustomer", method = RequestMethod.POST)
	@CrossOrigin
	public @ResponseBody String saveCustomer(@RequestBody Customer customerObj) {
		LOGGER.info(customlogger+"new Customer Details::" + customerObj.toString());
		String message = null;
		try {
			String uname = customerObj.getUsername();
			if (uname != null && !uname.isEmpty())
				if (checkAvailability(uname)) {
				    LOGGER.info(customlogger+"check availability serveice passed ... saving new customer details");
					customerService.saveCustomerDetails(customerObj);
					message = "Customer Details saved sucessfully";
				}
				else
					message = "Username already Exists.";
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	@RequestMapping(value="/checkusername/{username}", method = RequestMethod.GET)
	@Consumes({MediaType.TEXT_PLAIN})
	@CrossOrigin
	public boolean checkAvailability(@PathVariable String username) {
		LOGGER.info(customlogger+"Inside  checkAvailability service");
		boolean result = false;
		try {
			Customer custDetails =  customerService.getCustomerDetails(username);
			if (custDetails != null ) {
				result = false;
				LOGGER.info(customlogger+"customer details already exists");
			} else {
				result = true;
				LOGGER.info(customlogger+"customer details does not exists");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
	
	
	@RequestMapping(value="/username/{username}", method = RequestMethod.GET)
	@Consumes({MediaType.TEXT_PLAIN})
	@CrossOrigin
	public Customer getUserDetails(@PathVariable String username) {
		LOGGER.info(customlogger+"Inside  checkAvailability service");
		Customer custDetails = null;
		try {
			custDetails =  customerService.getCustomerDetails(username);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return custDetails;
	}
	
	
	@HystrixCommand(fallbackMethod = "getDefaultCustomer", commandKey = "getAllCustomers", groupKey = "template-customer-service", commandProperties = {
			@HystrixProperty(name = "circuitBreaker.sleepWindowInMilliseconds", value = "60000"),
			@HystrixProperty(name = "circuitBreaker.errorThresholdPercentage", value = "5"), })
	@RequestMapping(value="/all", method = RequestMethod.GET)
	@Produces(MediaType.APPLICATION_JSON)
	@CrossOrigin
	public @ResponseBody List<Customer> getAllCustomers() throws Exception {
		
		List<Customer> allcustomersList = new ArrayList<Customer>();
		try {
			allcustomersList = customerService.getAllCustomers();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		return allcustomersList;
	}
	
	@HystrixCommand(fallbackMethod = "getDefaultProduct")
	public List<Customer>  getDefaultCustomer() {

		throw new RuntimeException("No Record found.");
	}

	 @RequestMapping(value="/", method = RequestMethod.GET)
	 @CrossOrigin
	    public String home(){
	        return "redirect:/login";
	    }

}
