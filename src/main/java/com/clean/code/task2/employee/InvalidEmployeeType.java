package com.clean.code.task2.employee;

/**
 * Thrown when an unknown employee type is encountered.
 */
public class InvalidEmployeeType extends Exception {

    public InvalidEmployeeType(String type) {
        super("Invalid employee type: " + type);
    }
}
