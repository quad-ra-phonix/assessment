package com.assessment.customer.util.validationFactory;

public class ValidationFactory {

    public ValidationProcessor createValidatorProcessor(String processorType) {
        if (processorType == null || processorType.isEmpty())
            return null;
        switch (processorType) {
            case "Simple":
                return new SimpleValidation();
            case "Advanced":
                return new AdvancedValidation();

            default:
                throw new IllegalArgumentException("Unknown Processor Type: " + processorType);
        }
    }
}
