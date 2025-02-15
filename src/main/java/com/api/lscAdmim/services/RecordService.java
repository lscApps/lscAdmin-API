package com.api.lscAdmim.services;

import java.time.LocalDate;
import java.time.YearMonth;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.api.lscAdmim.dtos.RecordDTO;
import com.api.lscAdmim.entities.Record;
import com.api.lscAdmim.enums.RecordType;
import com.api.lscAdmim.repositories.RecordRepository;

@Service
public class RecordService {

    private final RecordRepository repository;

    @Autowired
    public RecordService(RecordRepository repository) {
        this.repository = repository;
    }

    public List<RecordDTO> saveRecords(List<RecordDTO> newRecords){
        List<Record> records = repository.saveAll(newRecords.stream().map(Record::new).toList());
        return records.stream().map(RecordDTO::new).toList();
    }

	public List<RecordDTO> getAll() {
		return repository.findAll().stream().map(RecordDTO::new).toList();
	}
    
    public List<RecordDTO> getExpensesByMonth(Integer year, Integer month){
    	YearMonth currentMonty = YearMonth.of(year, month);    	
    	LocalDate firstDay =currentMonty.atDay(1);
    	LocalDate lastDay = currentMonty.atEndOfMonth();
    	return repository.findByDateBetweenAndRecordTypeGreaterThan(firstDay, lastDay, RecordType.INCOME.getId()).stream().map(RecordDTO::new).toList();
    }

}
