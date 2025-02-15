package com.api.lscAdmim.entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import com.api.lscAdmim.dtos.RecordDTO;

import jakarta.annotation.Nonnull;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
public class Record implements Serializable{
	
	private static final long serialVersionUID = 5301098313869652719L;

	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nonnull
    private String description;

    @Nonnull
    private Float amount;

    @Nonnull
    private LocalDate date;

    @Nonnull
    private Long departmentId;

    private Integer recordType; // 0-INCOME // 1-ONE_TIME // 2-RECURRING

    private Integer recurringType; // 0-FIXED // 1-INSTALLMENT

    private Integer recurringCount;
    
    private Integer status; // 0 - INACTIVE // 1- ACTIVE


    public Record(RecordDTO dto){
        this.id = dto.getId();
        this.description = dto.getDescription();
        this.amount = dto.getAmount();
        this.date = LocalDate.parse(dto.getDate(), DateTimeFormatter.ofPattern("dd/MM/yyyy"));
        this.departmentId = dto.getDepartmentId();
        this.recordType = dto.getRecordType();
        this.recurringType = dto.getRecurringType();
        this.recurringCount = dto.getRecurringCount();
        this.status = dto.getStatus();
    }

}
