package com.clean.code.task2.employee;

import java.util.Objects;

/**
 * Value object representing a monetary amount in minor units (e.g. cents).
 */
public final class Money {

    private final long amountMinorUnits;

    public Money(long amountMinorUnits) {
        this.amountMinorUnits = amountMinorUnits;
    }

    public long getAmountMinorUnits() {
        return amountMinorUnits;
    }

    public Money add(Money other) {
        return new Money(amountMinorUnits + other.amountMinorUnits);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Money money = (Money) o;
        return amountMinorUnits == money.amountMinorUnits;
    }

    @Override
    public int hashCode() {
        return Objects.hash(amountMinorUnits);
    }
}
