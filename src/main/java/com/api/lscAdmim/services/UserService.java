package com.api.lscAdmim.services;

import com.api.lscAdmim.configs.JwtUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lscAdmim.dtos.UserDTO;
import com.api.lscAdmim.entities.User;
import com.api.lscAdmim.exceptions.ExistentUsernameException;
import com.api.lscAdmim.exceptions.UnauthorizedCreditialsException;
import com.api.lscAdmim.repositories.UserRepository;

@Service
public class UserService {
	
	private final UserRepository repo;

	private final JwtUtil jwtComponent;
	
	@Autowired
	public UserService(UserRepository repo, JwtUtil jwtComponent) {
		this.repo = repo;
		this.jwtComponent = jwtComponent;
	}
	
	public UserDTO getUserByLogin(UserDTO loginData) {
		User user = repo.findByUsernameAndPassword(loginData.getUsername(), loginData.getPassword())
				.orElseThrow(() ->new UnauthorizedCreditialsException("User not found for credetials informed"));

		UserDTO userDTO = new UserDTO(user);
		userDTO.setToken(jwtComponent.generateToken(userDTO.getUsername()));

		return userDTO;
	}
	
	
	public UserDTO saveUser(UserDTO newUser) {
		repo.findByUsername(newUser.getUsername()).ifPresent(user  ->{
			throw new ExistentUsernameException(String.format("Username %s already existent.", user.getUsername()));
		});
		
		return new UserDTO(repo.save(new User(newUser)));
	}

}
