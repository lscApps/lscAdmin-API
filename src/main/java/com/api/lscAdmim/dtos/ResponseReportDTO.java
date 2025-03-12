package com.api.lscAdmim.dtos;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class ResponseReportDTO {
	
	private List<RecordDTO> records;
	
	private String dtInit;
	
	private String dtEnd;

}
