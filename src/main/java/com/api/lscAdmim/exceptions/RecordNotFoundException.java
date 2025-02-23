package com.api.lscAdmim.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

import jakarta.persistence.EntityNotFoundException;

@ResponseStatus(HttpStatus.NOT_FOUND)
public class RecordNotFoundException extends EntityNotFoundException{

	private static final long serialVersionUID = 1L;
	
	public RecordNotFoundException(String message) {
		super(message);
	}
	

}
