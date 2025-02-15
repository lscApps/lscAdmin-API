package com.api.lscAdmim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecordType {

    INCOME(0, "Income"),
    ONE_TIME(1, "One-Time"),
    RECURRING(2, "Recurring");

    private final Integer id;
    private final String description;

}
