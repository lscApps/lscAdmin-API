package com.api.lscAdmim.controllers;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.api.lscAdmim.dtos.RecordDTO;
import com.api.lscAdmim.services.ExcelService;
import com.api.lscAdmim.services.RecordService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping("record")
public class RecordController {

    private final RecordService recordService;
    private final ExcelService excelService;

    @Autowired
    public RecordController(RecordService recordService, ExcelService excelService) {
        this.recordService = recordService;
        this.excelService = excelService;
    }

    @PutMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<Void> updateRecords(@RequestBody RecordDTO record){
    	recordService.updateRecord(record);
        return ResponseEntity.noContent().build();
    }
    
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecordDTO>> saveRecords(@RequestBody List<RecordDTO> records){
        return ResponseEntity.ok(recordService.saveRecords(records));
    }
    
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecordDTO>> getAllRecords(){
    	return ResponseEntity.ok(recordService.getAll());
    }
    
    @GetMapping(value = "/{year}/{month}")
    public ResponseEntity<List<RecordDTO>> getRecordsByMonth(@PathVariable("month") Integer monthId,
    		@PathVariable("year") Integer yearId){
    	
    	return ResponseEntity.ok(recordService.getRecordsByMonth(yearId, (monthId+1)));

    }

    @GetMapping(value = "/{year}")
    public ResponseEntity<List<RecordDTO>> getRecordsByYear(@PathVariable("year") Integer yearId){

    	return ResponseEntity.ok(recordService.getRecordsByYear(yearId));

    }

    @GetMapping
    public ResponseEntity<List<RecordDTO>> gerRecords(@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dtInit,
    		@RequestParam @DateTimeFormat(pattern = "dd/MM/yyyy") LocalDate dtEnd){

    	return ResponseEntity.ok(recordService.getRecordsInRange(dtInit, dtEnd));

    }

    @GetMapping(value = "/recurrents")
    public ResponseEntity<List<RecordDTO>> getRecurrentRecords(){
    	return ResponseEntity.ok(recordService.getRecurrentRecords());
    }
    

    @PostMapping(value = "/excel", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    public ResponseEntity<byte[]> exportExcel(@RequestBody List<RecordDTO> records) {
    	return ResponseEntity.ok()
    			.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=records.xlsx")
    			.contentType(MediaType.APPLICATION_OCTET_STREAM)
    			.body(excelService.generateFile(records));
    }
    
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Void> deleteRecord(@PathVariable("id") Long recordId){
		recordService.deleteRecord(recordId);
   		
    	return ResponseEntity.noContent().build();
    }

}
