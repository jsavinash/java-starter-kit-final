package com.javastarterkit.batch.writer;

import java.util.List;

import org.springframework.batch.item.ItemWriter;
import org.springframework.stereotype.Component;

import com.javastarterkit.batch.entity.UserRecord;

@Component
public class UserWriter implements ItemWriter<UserRecord> {

    @Override
    public void write(List<? extends UserRecord> items) throws Exception {
        System.out.println("Writing " + items.size() + " user records");
        for (UserRecord user : items) {
            System.out.println("  - " + user.getFirstName() + " " + user.getLastName() + " (" + user.getEmail() + ")");
        }
    }
}