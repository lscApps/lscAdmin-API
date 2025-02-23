package com.api.lscAdmim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecordType {

    INCOME(0, "Income"),
    EXPENSE(1, "Expense"),
    RECURRENT(2, "Recurrent");

    private final Integer id;
    
    private final String description;
    
    
    public static String getById(Integer id){
    	for (RecordType rt : RecordType.values()) {
			if (rt.getId() == id)
				return rt.getDescription();
		}
    	throw new RuntimeException(String.format("No RecordType found for ID: %d", id));
    }
    
}
