package com.api.lscAdmim.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
public class LscAdminServiceException extends RuntimeException{

	private static final long serialVersionUID = 1L;
	
	public LscAdminServiceException(String message) {
		super(message);
	}
	

}
