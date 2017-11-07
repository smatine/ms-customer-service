package com.sg.microservices.customer.entity;


import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import org.springframework.stereotype.Component;

@Entity
@Table(name="CUSTOMER_DETAILS")
@Component
public class Customer {
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "customerid") 
	private long  customerid;
	
	
	@Column(name = "username") 
	private String username;
	
	@Column(name = "password") 
	private String password;

	@Column(name = "firstname") 
	private String firstname;
	
	@Column(name = "lastname") 
	private String lastname;
	
	@Column(name = "email") 
	private String email;
	
	@Column(name = "address") 
	private String address;
	
	@Column(name="phnum")
	private long phnumber;

	public long getPhnumber() {
		return phnumber;
	}

	public void setPhnumber(long phnumber) {
		this.phnumber = phnumber;
	}

	public long getCustomerid() {
		return customerid;
	}

	public void setCustomerid(long customerid) {
		this.customerid = customerid;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getAddress() {
		return address;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	@Override
	public String toString() {
		return "Customer [customerid=" + customerid + ", username=" + username
				+ ", password=" + password + ", firstname=" + firstname
				+ ", lastname=" + lastname + ", email=" + email + ", address="
				+ address + ", phnumber=" + phnumber + "]";
	}

	public Customer(long customerid, String username, String password,
			String firstname, String lastname, String email, String address,
			long phnumber) {
		super();
		this.customerid = customerid;
		this.username = username;
		this.password = password;
		this.firstname = firstname;
		this.lastname = lastname;
		this.email = email;
		this.address = address;
		this.phnumber = phnumber;
	}

	
public Customer(){
	
}
	

}
