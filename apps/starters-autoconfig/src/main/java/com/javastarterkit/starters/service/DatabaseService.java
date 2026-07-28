// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.starters.service;

import edu.umd.cs.findbugs.annotations.SuppressFBWarnings;
import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import javax.sql.DataSource;
import org.springframework.stereotype.Service;

/**
 * Service demonstrating database connectivity using Spring Boot starters.
 */
@Service
public class DatabaseService {

    private final DataSource dataSource;

    @SuppressFBWarnings(value = "EI2", justification = "DataSource is managed by Spring and effectively immutable")
    public DatabaseService(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void testConnection() throws SQLException {
        try (Connection connection = dataSource.getConnection();
                Statement statement = connection.createStatement();
                ResultSet resultSet = statement.executeQuery("SELECT 1")) {
            if (resultSet.next()) {
                System.out.println("Database connection successful: " + resultSet.getInt(1));
            }
        }
    }
}
