package com.api.lscAdmim.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.EntityNotFoundException;

@ResponseStatus(HttpStatus.FORBIDDEN)
public class UnauthorizedCreditialsException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;
	
	public UnauthorizedCreditialsException(String message) {
		super(message);
	}
	

}
