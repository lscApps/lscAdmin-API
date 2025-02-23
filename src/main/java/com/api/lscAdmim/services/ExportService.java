package com.api.lscAdmim.services;

import java.util.List;

import com.api.lscAdmim.dtos.RecordDTO;

public interface ExportService {
	
	public byte[] generateFile(List<RecordDTO> records);

}
