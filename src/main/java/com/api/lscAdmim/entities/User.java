package com.api.lscAdmim.entities;

import java.io.Serializable;

import com.api.lscAdmim.dtos.UserDTO;
import com.google.gson.Gson;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity(name = "user_app")
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class User implements Serializable{
	

	private static final long serialVersionUID = -7343411094266208453L;
	
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;
	
	@Nonnull
	private String username;
	
	private String password;
	
	private String json_roles;
	
	public User(UserDTO dto) {
		this.id = dto.getId();
		this.username = dto.getUsername();
		this.password = dto.getPassword();
		this.json_roles = new Gson().toJson(dto.getRoles());
	}

}
