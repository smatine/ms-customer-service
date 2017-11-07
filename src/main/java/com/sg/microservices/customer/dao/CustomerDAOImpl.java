package com.sg.microservices.customer.dao;

import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.sg.microservices.customer.entity.Customer;


@Component
@Repository
public class CustomerDAOImpl  implements ICustomerDAO {

	
	 @PersistenceContext
	 private EntityManager entityManager;
	
	
	@Override
	public List<Customer> getCustomerDetailsbyUserId(String username) {
	
		 CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	     CriteriaQuery<Customer> criteriaQuery =criteriaBuilder.createQuery(Customer.class);
	     Root<Customer> user = criteriaQuery.from(Customer.class);
	     TypedQuery<Customer> typedQuery =null;
	
		try{
			
	        typedQuery = entityManager.createQuery(criteriaQuery.select(user)
		            .where(criteriaBuilder.and(criteriaBuilder.equal(user.get("username"),username))).distinct(true));
		}
		
		catch(Exception e){
			e.printStackTrace();
		}
		    return typedQuery.getResultList();
	}

	
	
	@Override
	public String saveCustomerDetails(Customer customerObj) {

		String result = null;
		try {
			entityManager.persist(customerObj);
			result = "Customer Deatils saved Sucessfully";
		} catch (Exception e) {
			e.printStackTrace();
		}

		return result;

	}

	@Override
	public List<Customer> getAllCustomers() {

		
		 CriteriaBuilder criteriaBuilder = entityManager.getCriteriaBuilder();
	     CriteriaQuery<Customer> criteriaQuery =criteriaBuilder.createQuery(Customer.class);
	     Root<Customer> user = criteriaQuery.from(Customer.class);
	     TypedQuery<Customer> typedQuery =null;
	     
		try {

			
		
			typedQuery = entityManager.createQuery(criteriaQuery.select(user));
		}

		catch (Exception e) {
			e.printStackTrace();
		}

		return typedQuery.getResultList();
	}
}
