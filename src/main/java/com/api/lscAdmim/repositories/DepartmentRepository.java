package com.api.lscAdmim.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.lscAdmim.entities.Department;

@Repository
public interface DepartmentRepository extends JpaRepository<Department, Long>{
	

}
