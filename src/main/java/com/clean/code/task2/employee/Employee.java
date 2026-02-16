package com.clean.code.task2.employee;

/**
 * Abstract base for all employee types (OCP: new types via new subclasses).
 */
public abstract class Employee {

    private final String name;

    protected Employee(String name) {
        if (name == null || name.isBlank()) {
            throw new IllegalArgumentException("Employee name must not be null or blank");
        }
        this.name = name.trim();
    }

    public String getName() {
        return name;
    }

    /**
     * Calculates pay for this employee according to their type.
     */
    public abstract Money calculatePay();

    /**
     * Calculates bonus for this employee according to their type.
     */
    public abstract Money calculateBonus();
}
