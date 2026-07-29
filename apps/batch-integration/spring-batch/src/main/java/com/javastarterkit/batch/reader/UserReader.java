// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.batch.reader;

import com.javastarterkit.batch.entity.UserRecord;
import java.util.Arrays;
import java.util.List;
import org.springframework.batch.item.ItemReader;
import org.springframework.stereotype.Component;

@Component
public class UserReader implements ItemReader<UserRecord> {

    private int index = 0;
    private final List<UserRecord> users = Arrays.asList(
            new UserRecord(1L, "John", "Doe", "john.doe@example.com", "Engineering"),
            new UserRecord(2L, "Jane", "Smith", "jane.smith@example.com", "Marketing"),
            new UserRecord(3L, "Bob", "Johnson", "bob.johnson@example.com", "Sales"),
            new UserRecord(4L, "Alice", "Williams", "alice.williams@example.com", "HR"),
            new UserRecord(5L, "Charlie", "Brown", "charlie.brown@example.com", "Finance"));

    @Override
    public UserRecord read() throws Exception {
        if (index < users.size()) {
            return users.get(index++);
        }
        index = 0; // Reset for next run
        return null; // Signals end of data
    }
}
