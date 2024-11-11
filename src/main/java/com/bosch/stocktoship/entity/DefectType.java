package com.bosch.stocktoship.entity;

/**
 * @author BAP3COB
 * Enum representing different types of defects that can occur in a product.
 * Each defect type has a label that provides a human-readable description.
 */
public enum DefectType {
    DIMENSIONAL_ERROR("Dimensional Error"),
    DEFECTIVE_PART("Defective Part"),
    MISSING_PART("Missing Part");

    private final String label;

    DefectType(String label) {
        this.label = label;
    }

    public String getLabel() {
        return label;
    }

    public static DefectType fromChoice(int choice) {
        switch (choice) {
            case 1:
                return DIMENSIONAL_ERROR;
            case 2:
                return DEFECTIVE_PART;
            case 3:
                return MISSING_PART;
            default:
                return null;
        }
    }
}

