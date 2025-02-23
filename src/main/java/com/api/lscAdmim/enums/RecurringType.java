package com.api.lscAdmim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecurringType {
	
	NA(0, "N/A"),
    FIXED(1, "Fixed"),
    INSTALLMENT(2, "Installment");

    private final Integer id;

    private final String description;
    
	public static String getById(Integer id) {
		for (RecurringType rt : RecurringType.values()) {
			if (rt.getId() == id)
				return rt.getDescription();
		}
		throw new RuntimeException(String.format("No RecurringType found for ID: %d", id));
	}
}
