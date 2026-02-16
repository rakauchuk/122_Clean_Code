package com.clean.code.task2.employee;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class EmployeePayTest {

    private final PayrollService payroll = new PayrollService();

    @Test
    void commissionedEmployee_payAndBonus() {
        Employee e = new CommissionedEmployee("Alice", 50_00, 10_00, 5);
        assertEquals(new Money(50_00 + 5 * 10_00), payroll.calculatePay(e));
        assertEquals(new Money(5 * 100), payroll.calculateBonus(e));
    }

    @Test
    void hourlyEmployee_payAndBonus() {
        Employee e = new HourlyEmployee("Bob", 20_00, 40, 5);
        // 40*20 + 5*20*2 = 800 + 200 = 1000
        assertEquals(new Money(1000_00), payroll.calculatePay(e));
        assertEquals(new Money(5 * 50), payroll.calculateBonus(e));
    }

    @Test
    void salariedEmployee_payAndBonus() {
        Employee e = new SalariedEmployee("Carol", 5000_00);
        assertEquals(new Money(5000_00), payroll.calculatePay(e));
        assertEquals(new Money(500_00), payroll.calculateBonus(e));
    }

    @Test
    void salariedEmployee_withPerformanceFactor() {
        Employee e = new SalariedEmployee("Dave", 5000_00, 1.2);
        assertEquals(new Money(6000_00), payroll.calculatePay(e));
    }

    @Test
    void employee_nameRequired() {
        assertThrows(IllegalArgumentException.class, () -> new SalariedEmployee("", 1000));
        assertThrows(IllegalArgumentException.class, () -> new SalariedEmployee(null, 1000));
    }
}
