package com.clean.code.task4;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Intentional issues for SonarQube analysis (Task 4).
 * See README.md / SONAR_ISSUES.md for the list of deliberate flaws.
 * Sonar remarks: S3649 (SQL injection), S2095 (resource leak), division-by-zero bug, duplication/magic numbers.
 */
@RestController
public class SampleController {

    private static final String JDBC_URL = "jdbc:h2:mem:testdb";
    private static final String USER = "sa";
    private static final String PASSWORD = "password";

    /**
     * Intentional: SQL Injection vulnerability — user input concatenated into query.
     * SonarQube: S3649 (JDBC queries should use PreparedStatement).
     * Resource leak: Connection and Statement not closed — SonarQube S2095 / try-with-resources.
     */
    @GetMapping("/unsafe")
    public String unsafeMethod(@RequestParam(defaultValue = "") String input) {
        try {
            Connection connection = DriverManager.getConnection(JDBC_URL, USER, PASSWORD);
            Statement statement = connection.createStatement();
            // S3649 — SQL Injection: concatenating user input into query
            statement.executeQuery("SELECT * FROM user_data WHERE user_name = '" + input + "'");
            return "Data Retrieved";
        } catch (SQLException e) {
            return "Error in SQL Handling";
        }
    }

    /**
     * Intentional: Division by zero — runtime ArithmeticException.
     * SonarQube: Bug (division by zero).
     */
    @GetMapping("/logic-error")
    public int faultyLogic() {
        int a = 10;
        int b = 0;
        return a / b; // Sonar: division by zero
    }

    /**
     * Intentional code smells for SonarQube:
     * - Long method / high cognitive complexity
     * - Duplicated code (repeated doSomething with same args) — DRY
     * - Magic numbers (1, 2, 3, 5, 6, 7, 10)
     */
    @GetMapping("/smell")
    public List<String> longMethodWithDuplication(@RequestParam int type) {
        List<String> result = new ArrayList<>();
        if (type == 1) {
            result.add(doSomething(1, 2, 3));  // Sonar: duplication
            result.add(doSomething(1, 2, 3));
            result.add(doSomething(2, 3, 4));
            result.add(doSomething(2, 3, 4));
        } else if (type == 2) {
            result.add(doSomething(5, 6, 7));  // Sonar: duplication, magic numbers
            result.add(doSomething(5, 6, 7));
        } else {
            for (int i = 0; i < 10; i++) {     // Sonar: magic number 10
                result.add(doSomething(i, i + 1, i + 2));
            }
        }
        return result;
    }

    private String doSomething(int a, int b, int c) {
        return String.valueOf(a + b + c);
    }
}
