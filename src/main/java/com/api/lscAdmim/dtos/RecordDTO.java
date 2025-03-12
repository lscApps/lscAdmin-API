package com.api.lscAdmim.dtos;

import java.io.Serializable;
import java.time.format.DateTimeFormatter;

import com.api.lscAdmim.entities.Record;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter
@Setter
public class RecordDTO implements Serializable {

	private static final long serialVersionUID = 8410532094005381157L;

	private Long id;

    private String description;

    private Float amount;

    private String date;

    private Long departmentId;

    private int recordType; // 0-INCOME // 1-ONE_TIME // 2-RECURRING

    private int recurringType; // 0-FIXED // 1-INSTALLMENT

    private int recurringCount;
    
    private int status; // 0 - INACTIVE // 1- ACTIVE

    private String created;


    public RecordDTO(Record entity){
        this.id = entity.getId();
        this.description = entity.getDescription();
        this.amount = entity.getAmount();
        this.date =entity.getDate().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.departmentId = entity.getDepartment().getId();
        this.recordType = entity.getRecordType();
        this.recurringType = entity.getRecurringType();
        this.recurringCount = entity.getRecurringCount();
        this.status = entity.getStatus();
        this.created = entity.getCreated().format(DateTimeFormatter.ofPattern("dd/MM/yyyy"));
    }

}
