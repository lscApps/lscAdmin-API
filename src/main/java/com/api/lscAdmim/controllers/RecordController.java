package com.api.lscAdmim.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.api.lscAdmim.dtos.RecordDTO;
import com.api.lscAdmim.services.RecordService;

@RestController
@RequestMapping("record")
public class RecordController {

    private final RecordService recordService;

    @Autowired
    public RecordController(RecordService recordService) {
        this.recordService = recordService;
    }
    
    
    @PostMapping(consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecordDTO>> saveRecords(@RequestBody List<RecordDTO> records){
        return ResponseEntity.ok(recordService.saveRecords(records));
    }
    
    @GetMapping(value = "/getAll", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<List<RecordDTO>> getAllRecords(){
    	return ResponseEntity.ok(recordService.getAll());
    }
    
    @GetMapping(value = "/monthExpense/{year}/{month}")
    public ResponseEntity<List<RecordDTO>> getExpensesByMonth(@PathVariable("month") Integer monthId,
    		@PathVariable("year") Integer yearId){
    	
    	return ResponseEntity.ok(recordService.getExpensesByMonth(yearId, monthId));
    	
    }
    

}
