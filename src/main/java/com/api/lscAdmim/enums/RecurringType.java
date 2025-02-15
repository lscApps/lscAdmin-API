package com.api.lscAdmim.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public enum RecurringType {

    FIXED(0, "Fixed"),
    INSTALLMENT(1, "Installment");

    private final Integer id;

    private final String description;
}
