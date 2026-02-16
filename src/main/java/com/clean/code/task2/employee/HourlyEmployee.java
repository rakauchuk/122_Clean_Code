package com.clean.code.task2.employee;

/**
 * Employee paid by the hour, with optional overtime.
 */
public final class HourlyEmployee extends Employee {

    private final long hourlyRateMinor;
    private final int hoursWorked;
    private final int overtimeHours;
    private static final int OVERTIME_MULTIPLIER = 2;

    public HourlyEmployee(String name, long hourlyRateMinor, int hoursWorked, int overtimeHours) {
        super(name);
        this.hourlyRateMinor = hourlyRateMinor;
        this.hoursWorked = hoursWorked;
        this.overtimeHours = Math.max(0, overtimeHours);
    }

    @Override
    public Money calculatePay() {
        long regular = (long) hoursWorked * hourlyRateMinor;
        long overtime = (long) overtimeHours * hourlyRateMinor * OVERTIME_MULTIPLIER;
        return new Money(regular + overtime);
    }

    @Override
    public Money calculateBonus() {
        return new Money(overtimeHours * 50); // example: 50 minor units per overtime hour
    }
}
