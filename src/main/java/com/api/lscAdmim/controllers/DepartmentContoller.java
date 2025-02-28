package com.api.lscAdmim.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.lscAdmim.dtos.DepartmentDTO;
import com.api.lscAdmim.services.DepartmentService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("department")
public class DepartmentContoller {
	
	private final DepartmentService service;
	
	@Autowired
	public DepartmentContoller(DepartmentService service) {
		this.service = service;
	}
	
	@PostMapping
	public ResponseEntity<DepartmentDTO> save(@RequestBody DepartmentDTO newDepartment){		
		return ResponseEntity.ok(this.service.save(newDepartment));		
	}
	
	@GetMapping("/getAll")
	public ResponseEntity<List<DepartmentDTO>> getAll(){
		return ResponseEntity.ok(this.service.getAll());
	}
	
	@PutMapping
	public ResponseEntity<Void> update(@RequestBody DepartmentDTO newData){
		this.service.update(newData);
		return ResponseEntity.noContent().build();
	}
	
	@DeleteMapping("/{id}")
	public ResponseEntity<Void>delete(@PathVariable("id") Long id){
		this.service.deleteById(id);
		return ResponseEntity.noContent().build();
	}
}
