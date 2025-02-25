package com.api.lscAdmim.dtos;

import java.util.List;

import com.api.lscAdmim.entities.User;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Getter
public class UserDTO {
	
	private Long id;
	private String username;
	private String password;	
	private List<String> roles;
	
	public UserDTO(User entity) {
		this.id = entity.getId();
		this.username = entity.getUsername();	
		this.roles = new Gson().fromJson(entity.getJson_roles(), new TypeToken<List<String>>() {}.getType());
	}
	
}
