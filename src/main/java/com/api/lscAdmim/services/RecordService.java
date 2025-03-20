package com.api.lscAdmim.services;

import java.time.LocalDate;
import java.time.YearMonth;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lscAdmim.dtos.RecordDTO;
import com.api.lscAdmim.dtos.ResponseReportDTO;
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

	private final DepartmentService depService;

	private final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern("dd/MM/yyyy");

	@Autowired
	public RecordService(RecordRepository repository, DepartmentService depService) {
		this.repository = repository;
		this.depService = depService;
	}

	public void updateRecord(RecordDTO dto) {
		try {
			Record entity = repository.getReferenceById(dto.getId());
			entity.updateValues(dto, this.depService.getEntityById(dto.getDepartmentId()));
			repository.save(entity);
		} catch (Exception e) {
			log.error("Error updating record: {}", e.getMessage());
			throw e;
		}

	}

	public List<RecordDTO> saveRecords(List<RecordDTO> newRecords) {
		List<Record> records = repository.saveAll(newRecords.stream()
				.map(dto -> new Record(dto, this.depService.getEntityById(dto.getDepartmentId()))).toList());
		return records.stream().map(RecordDTO::new).toList();
	}

	public List<RecordDTO> getAll() {
		return repository.findAll().stream().map(RecordDTO::new).toList();
	}

	public ResponseReportDTO getRecordsByMonth(Integer year, Integer month) {
		YearMonth requestedMonth = YearMonth.of(year, month);

		LocalDate dtInit = requestedMonth.atDay(1);
		LocalDate dtEnd = requestedMonth.atEndOfMonth();

		List<RecordDTO> records = repository
				.findByDateBetweenAndStatusOrderByDate(dtInit, dtEnd, RecordStatus.ACTIVE.getValue()).stream()
				.map(RecordDTO::new).toList();

		return new ResponseReportDTO(records, dtInit.format(DATE_FORMATTER), dtEnd.format(DATE_FORMATTER));

	}

	public ResponseReportDTO getRecordsInRange(LocalDate dtInit, LocalDate dtEnd) {

		List<RecordDTO> records = repository
				.findByDateBetweenAndStatusOrderByDate(dtInit, dtEnd, RecordStatus.ACTIVE.getValue()).stream()
				.map(RecordDTO::new).toList();

		return new ResponseReportDTO(records, dtInit.format(DATE_FORMATTER), dtEnd.format(DATE_FORMATTER));
	}

	public ResponseReportDTO getRecordsByYear(Integer year) {
		LocalDate dtInit = YearMonth.of(year, 1).atDay(1);
		LocalDate dtEnd = YearMonth.of(year, 12).atEndOfMonth();

		List<RecordDTO> records = repository.findByDateBetweenOrderByDateDesc(dtInit, dtEnd).stream()
				.map(RecordDTO::new).toList();

		return new ResponseReportDTO(records, dtInit.format(DATE_FORMATTER), dtEnd.format(DATE_FORMATTER));
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
