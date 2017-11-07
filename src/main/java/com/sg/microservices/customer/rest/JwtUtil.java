package com.sg.microservices.customer.rest;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureException;



import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class JwtUtil {
	
    private static final Logger LOGGER = LoggerFactory.getLogger(JwtUtil.class);
    private static final String signingKey = "my-jwt-secretkey";
    
	public static String getSubject(String token) {
		
		LOGGER.info("JWTUtil Service Started::::::");
		LOGGER.info("Inside getSubject() method::::");
		Jws<Claims> claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
		String username = claims.getBody().getSubject();
		LOGGER.info("Subject :::"+username);
		return username;
	}

	public static String getPassword(String token) {
		LOGGER.info("JWTUtil Service Started::::::");
		LOGGER.info("Inside getPassword() method::::");
		Jws<Claims> claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(token);
		Object password = claims.getBody().get("password");
		LOGGER.info("password :::"+password);
		return password.toString();
	}

	
	public static boolean validateJWT(String compactJws) {
		 System.out.println("validateJWT Started:::::");
		 boolean isValid = false;
       try {
           Jwts.parser().setSigningKey(signingKey).parseClaimsJws(compactJws);
           isValid = true;
           Claims claims = Jwts.parser().setSigningKey(signingKey).parseClaimsJws(compactJws).getBody();
           System.out.println("claims.getSubject():::"+claims.getSubject());

       } catch (SignatureException e) {
              System.out.println("Exception :::"+e);
       }
       return isValid;
	}


	}
