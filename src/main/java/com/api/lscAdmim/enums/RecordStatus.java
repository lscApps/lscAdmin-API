package com.api.lscAdmim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecordStatus {
	
	ACTIVE(1),
	INACTIVE(0);
	
	private final Integer value;

}
