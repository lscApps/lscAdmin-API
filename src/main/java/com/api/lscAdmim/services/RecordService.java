package com.api.lscAdmim.services;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lscAdmim.dtos.RecordDTO;
import com.api.lscAdmim.entities.Record;
import com.api.lscAdmim.enums.RecordStatus;
import com.api.lscAdmim.enums.RecordType;
import com.api.lscAdmim.exceptions.RecordNotFoundException;
import com.api.lscAdmim.repositories.RecordRepository;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class RecordService {

	private final RecordRepository repository;

	@Autowired
	public RecordService(RecordRepository repository) {
		this.repository = repository;
	}

	public void updateRecord(RecordDTO dto) {
		try {
			Record entity = repository.getReferenceById(dto.getId());
			entity.updateValues(dto);
			repository.save(entity);
		} catch (Exception e) {
			log.error(String.format("Error updating record: %s", e.getMessage()));
			throw e;
		}

	}

	public List<RecordDTO> saveRecords(List<RecordDTO> newRecords) {
		List<Record> records = repository.saveAll(newRecords.stream().map(Record::new).toList());
		return records.stream().map(RecordDTO::new).toList();
	}

	public List<RecordDTO> getAll() {
		return repository.findAll().stream().map(RecordDTO::new).toList();
	}

	public List<RecordDTO> getRecordsByMonth(Integer year, Integer month) {
		YearMonth currentMonty = YearMonth.of(year, month);

		return repository.findByDateBetweenAndStatusOrderByDate(currentMonty.atDay(1), currentMonty.atEndOfMonth(),
				RecordStatus.ACTIVE.getValue()).stream().map(RecordDTO::new).toList();
	}

	public List<RecordDTO> getRecordsInRange(LocalDate dtInit, LocalDate dtEnd) {
		return repository.findByDateBetweenAndStatusOrderByDate(dtInit, dtEnd, RecordStatus.ACTIVE.getValue()).stream()
				.map(RecordDTO::new).toList();
	}

	public List<RecordDTO> getRecordsByYear(Integer year) {
		YearMonth initialMonth = YearMonth.of(year, 1);
		YearMonth endMonth = YearMonth.of(year, 12);

		return repository.findByDateBetweenOrderByDate(initialMonth.atDay(1), endMonth.atEndOfMonth()).stream()
				.map(RecordDTO::new).toList();
	}

	public List<RecordDTO> getRecurrentRecords() {

		return repository.findByRecordTypeAndStatusOrderByRecordType(RecordType.RECURRENT.getId(),
				RecordStatus.ACTIVE.getValue()).stream().map(RecordDTO::new).toList();
	}

	public void deleteRecord(Long recordId) {
		repository.findById(recordId).ifPresentOrElse(entity -> {
			repository.deleteById(recordId);
		}, () -> {
			throw new RecordNotFoundException(String.format("Record not found for ID: %d", recordId));
		});

	}

}
