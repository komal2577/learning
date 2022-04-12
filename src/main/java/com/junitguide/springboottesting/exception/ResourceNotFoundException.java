package com.junitguide.springboottesting.exception;

import org.springframework.web.bind.annotation.ResponseStatus;

public class ResourceNotFoundException extends RuntimeException {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 2444511729582444476L;

	public ResourceNotFoundException(String message){
        super(message);
    }

}
