package com.api.lscAdmim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecordType {

    INCOME(0),
    ONE_TIME(1),
    RECURRING(2);

    private final Integer id;

}
