package com.api.lscAdmim.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.EntityNotFoundException;

@ResponseStatus(HttpStatus.UNPROCESSABLE_ENTITY)
public class ExistentUsernameException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;
	
	public ExistentUsernameException(String message) {
		super(message);
	}
	

}
