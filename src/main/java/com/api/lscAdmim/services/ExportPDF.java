package com.api.lscAdmim.services;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import com.api.lscAdmim.dtos.RecordDTO;
import com.api.lscAdmim.exceptions.LscAdminServiceException;
import com.openhtmltopdf.pdfboxout.PdfRendererBuilder;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class ExportPDF{
	
	private static final String K_INCOME = "incomes";
	private static final String K_EXPENSE = "expenses";
	private static final String K_BALANCE = "balance";
	
	private static final String POSITIVE_FORMAT = " \u20AC%.2f";
	private static final String NEGATIVE_FORMAT = "- \u20AC%.2f";
	
	
	@Value("classpath:templates/report-template.html")
    private Resource template;
	
	private final DepartmentService depService;
	
	@Autowired
	public ExportPDF(DepartmentService depService) {
		this.depService = depService;
	}
	

	public byte[] generateFile(List<RecordDTO> records, LocalDate dtInit, LocalDate dtEnd) {
			
		try (InputStream inputStream = template.getInputStream()){
			
			String htmlContent = new String(inputStream.readAllBytes());
			
			HashMap<String, String> sums = this.calculateBalance(records);
			
			boolean balanceNegative = sums.get(K_BALANCE).startsWith("-");
			
			DateTimeFormatter dateFormat = DateTimeFormatter.ofPattern("dd/MM/yyyy");
			
			String filledHtml = htmlContent.replace("{{date}}", LocalDate.now().toString())
					.replace("{{table_rows}}", generateTableRows(records))
					.replace("{{total_income}}", sums.get(K_INCOME))
					.replace("{{total_expenses}}", sums.get(K_EXPENSE))
					.replace("{{balance}}", sums.get(K_BALANCE))
					.replace("{{BALANCE_CLASS}}",  balanceNegative ? "negative" : "positive")
					.replace("{{dateInit}}", dtInit.format(dateFormat))
					.replace("{{dateEnd}}", dtEnd.format(dateFormat));	
						
			return convertHtmlToPdf(filledHtml);
			
		} catch (IOException e) {
			String msg = String.format("Error generating PDF report: %s", e.getMessage());
			log.error(msg);
			throw new LscAdminServiceException(msg);
		}		
	}
	
	private String getAmountFormatted(RecordDTO dto) {
		return String.format(dto.getRecordType() > 0 ? NEGATIVE_FORMAT : POSITIVE_FORMAT, dto.getAmount());
	}
	
	private HashMap<String, String> calculateBalance(List<RecordDTO> records) {
		Double totalIncomes = records.stream().mapToDouble(rec -> {
			return rec.getRecordType() == 0 ? rec.getAmount() : 0f;
		}).sum();
		
		Double totalExpense = records.stream().mapToDouble(rec -> {
			return rec.getRecordType() > 0 ? rec.getAmount() : 0f;
		}).sum();
		
		Double balance = totalIncomes - totalExpense; 
		
		HashMap<String, String> sums = new HashMap<String, String>();
		sums.put(K_INCOME, String.format(POSITIVE_FORMAT, totalIncomes.floatValue()));
		sums.put(K_EXPENSE, String.format(NEGATIVE_FORMAT, totalExpense.floatValue()));
		sums.put(K_BALANCE, String.format(balance > 0? POSITIVE_FORMAT : NEGATIVE_FORMAT, Math.abs(balance.floatValue())));
		
		return sums;
	}
	
	
	private String generateTableRows(List<RecordDTO> records) {
		
		List<String> data = records.stream().map(rec -> { 
			String depName = this.depService.getEntityById(rec.getDepartmentId()).getName();
			String amount = this.getAmountFormatted(rec);
			boolean negativeSignal = amount.startsWith("-");
			String amountClass = negativeSignal ? "amountOut" : "amountIn";
			
			return String.format("<tr><td class='tac'>%s</td>"
					+ "<td>%s</td>"
					+ "<td class='tac'>%s</td>"
					+ "<td class='tar %s'>%s</td></tr>", rec.getDate(), rec.getDescription(), depName, amountClass, amount);
		}).toList();
		
        return String.join("\n", data);
    }
	
	private byte[] convertHtmlToPdf(String html) throws IOException {
        try (ByteArrayOutputStream outputStream = new ByteArrayOutputStream()) {
            PdfRendererBuilder builder = new PdfRendererBuilder();
            builder.useFastMode();
            builder.toStream(outputStream);
            builder.withHtmlContent(html, "file:///");
            builder.run();
            return outputStream.toByteArray();
        }
    }

}
