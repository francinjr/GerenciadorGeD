package com.francinjr.xpenses.domain.model;

public enum FinanceType {
    EARNING("Ganho"),
    EXPENSE("Despesa");

    private final String typeDescription;

    FinanceType(String typeDescription) {
        this.typeDescription = typeDescription;
    }

    public String getTypeDescription() {
        return typeDescription;
    }
}
