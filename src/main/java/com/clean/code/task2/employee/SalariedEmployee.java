package com.clean.code.task2.employee;

/**
 * Employee with a fixed monthly salary.
 */
public final class SalariedEmployee extends Employee {

    private final long salaryMinorPerPeriod;
    private final double performanceFactor; // e.g. 1.0 = 100%, 1.1 = 110%

    public SalariedEmployee(String name, long salaryMinorPerPeriod) {
        this(name, salaryMinorPerPeriod, 1.0);
    }

    public SalariedEmployee(String name, long salaryMinorPerPeriod, double performanceFactor) {
        super(name);
        this.salaryMinorPerPeriod = salaryMinorPerPeriod;
        this.performanceFactor = performanceFactor;
    }

    @Override
    public Money calculatePay() {
        return new Money((long) (salaryMinorPerPeriod * performanceFactor));
    }

    @Override
    public Money calculateBonus() {
        return new Money(salaryMinorPerPeriod / 10); // example: 10% of salary
    }
}
