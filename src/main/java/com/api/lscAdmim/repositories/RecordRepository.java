package com.api.lscAdmim.repositories;

import com.api.lscAdmim.entities.Record;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RecordRepository extends JpaRepository<Record, Long> {
	
	public List<Record> findByDateBetweenAndStatusOrderByDate(LocalDate dtStart, LocalDate dtEnd, Integer status);

	public List<Record> findByDateBetweenOrderByDate(LocalDate dtStart, LocalDate dtEnd);

	public List<Record> findByRecordTypeAndStatusOrderByRecordType(Integer rType, Integer status);

}
