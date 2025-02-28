package com.api.lscAdmim.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lscAdmim.dtos.DepartmentDTO;
import com.api.lscAdmim.entities.Department;
import com.api.lscAdmim.exceptions.LscAdminServiceException;
import com.api.lscAdmim.repositories.DepartmentRepository;

import jakarta.persistence.EntityNotFoundException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class DepartmentService {

	private final DepartmentRepository repo;

	@Autowired
	public DepartmentService(DepartmentRepository repo) {
		this.repo = repo;
	}

	public DepartmentDTO save(DepartmentDTO newDepartment) {
		try {
			Department entity = this.repo.save(new Department(newDepartment));
			return new DepartmentDTO(entity);
		} catch (Exception e) {
			String message = String.format("Error saving  departament %s: %s",newDepartment.getName(), e.getMessage());
			log.error(message);
			throw new LscAdminServiceException(message);			
		}
	}

	public List<DepartmentDTO> getAll() {
		try {
			return this.repo.findAll().stream().map(DepartmentDTO::new).toList();
		} catch (Exception e) {
			String message = String.format("Error fetching all departaments: %s", e.getMessage());
			log.error(message);
			throw new LscAdminServiceException(message);			
		}
	}

	public void update(DepartmentDTO depDTO) {
		try {
			Department depEntity = this.repo.findById(depDTO.getId()).orElseThrow(() -> new EntityNotFoundException(
					String.format("Department Not Found with id: %d", depDTO.getId())));
			
			depEntity.updateValues(depDTO);
			this.repo.save(depEntity);

		} catch (EntityNotFoundException nfe) {
			log.error(nfe.getMessage());
			throw nfe;
		} catch (Exception e) {
			String message = String.format("Error updating Departament: %s",e.getMessage());
			log.error(message);
			throw new LscAdminServiceException(message);			
		}
	}

	public void deleteById(Long id) {
		try {
			this.repo.deleteById(id);			
		} catch (Exception e) {
			String message = String.format("Error deleting Departament with ID[%d]: %s", id, e.getMessage());
			log.error(message);
			throw new LscAdminServiceException(message);			
		}
		
	}

}
