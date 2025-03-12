package com.api.lscAdmim.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import com.api.lscAdmim.dtos.DepartmentDTO;

import jakarta.annotation.Nonnull;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Department implements Serializable{
	
	private static final long serialVersionUID = 2187102074773658488L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id; 
	
	@Nonnull
	private String name;
	
	@Nonnull
	private String manager;
	
	@OneToMany(mappedBy = "department", cascade = CascadeType.ALL, orphanRemoval = true)
	private List<Record> records = new ArrayList<>();
	
	
	public Department(DepartmentDTO dto) {
		this.id = dto.getId();
		this.name = dto.getName();
		this.manager = dto.getManager();
	}

	public void updateValues(DepartmentDTO dto) {
		this.name = dto.getName();
		this.manager = dto.getManager();
	}
	
}
