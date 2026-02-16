package com.clean.code.task2.employee;

/**
 * Client-facing service: uses polymorphism instead of switch on type.
 */
public final class PayrollService {

    /**
     * Calculates pay for any employee (no type switch).
     */
    public Money calculatePay(Employee employee) {
        return employee.calculatePay();
    }

    /**
     * Calculates bonus for any employee (no type switch).
     */
    public Money calculateBonus(Employee employee) {
        return employee.calculateBonus();
    }
}
