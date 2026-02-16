package com.clean.code.task2.employee;

/**
 * Employee paid by commission (e.g. sales).
 */
public final class CommissionedEmployee extends Employee {

    private final long baseAmountMinor;
    private final long commissionAmountMinor;
    private final int unitsSold;

    public CommissionedEmployee(String name, long baseAmountMinor, long commissionAmountMinor, int unitsSold) {
        super(name);
        this.baseAmountMinor = baseAmountMinor;
        this.commissionAmountMinor = commissionAmountMinor;
        this.unitsSold = unitsSold;
    }

    @Override
    public Money calculatePay() {
        return new Money(baseAmountMinor + (long) unitsSold * commissionAmountMinor);
    }

    @Override
    public Money calculateBonus() {
        return new Money(unitsSold * 100); // example: 100 minor units per unit sold
    }
}
