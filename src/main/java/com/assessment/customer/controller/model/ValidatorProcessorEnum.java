package com.assessment.customer.controller.model;

public enum ValidatorProcessorEnum {
    SIMPLE("Simple"),
    ADVANCED("Advanced");

    private final String label;

    ValidatorProcessorEnum(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }
}
