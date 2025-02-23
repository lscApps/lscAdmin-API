package com.api.lscAdmim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecordStatus {
	
	ACTIVE(1, "Active"),
	INACTIVE(0, "Inactive");
	
	private final Integer value;
	
	private final String description;
	
	public static String getById(Integer id) {
		for (RecordStatus rs : RecordStatus.values()) {
			if (rs.getValue() == id)
				return rs.getDescription();
		}
		throw new RuntimeException(String.format("No RecordStatus found for ID: %d", id));
	}
}
