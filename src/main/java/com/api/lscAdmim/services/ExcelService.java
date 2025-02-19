package com.api.lscAdmim.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import com.api.lscAdmim.dtos.RecordDTO;

@Service
public class ExcelService {
	
	
	public byte[] generateExcel(List<RecordDTO> records) throws IOException{
		
		Workbook wb = new XSSFWorkbook();
		Sheet sheet = wb.createSheet("Records");
		
		
		//Creating Headers
		Row hr = sheet.createRow(0);
		List<String> columns =Arrays.asList("Id", "Department", "Description", "Type", "Amount", "Date", "Recurring Type", 
				"Installment Count",  "Creation Date", "Status");
		
		IntStream.range(0, columns.size())
			.forEach(index -> {
				Cell cell = hr.createCell(index);
				cell.setCellValue(columns.get(index));
				cell.setCellStyle(createHeaderStyle(wb));
			});
		
		
		IntStream.range(0, records.size())
			.forEach(index -> {
				int rowIndex = index+1;
				Row row = sheet.createRow(rowIndex);
							
				row.createCell(0).setCellValue(records.get(index).getId());
				row.createCell(1).setCellValue(records.get(index).getDepartmentId());
				row.createCell(2).setCellValue(records.get(index).getDescription());
				row.createCell(3).setCellValue(records.get(index).getRecordType());
				row.createCell(4).setCellValue(records.get(index).getAmount());
				row.createCell(5).setCellValue(records.get(index).getDate());
				row.createCell(6).setCellValue(records.get(index).getRecurringType());
				row.createCell(7).setCellValue(records.get(index).getRecurringCount());
				row.createCell(8).setCellValue(records.get(index).getCreated());
				row.createCell(9).setCellValue(records.get(index).getStatus());
				
			});
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		wb.write(outputStream);
		wb.close();
		
		return outputStream.toByteArray();
		
	}
	
	private CellStyle createHeaderStyle(Workbook workbook) {
        CellStyle style = workbook.createCellStyle();
        Font font = workbook.createFont();
        font.setBold(true);
        style.setFont(font);
        return style;
    }

}
