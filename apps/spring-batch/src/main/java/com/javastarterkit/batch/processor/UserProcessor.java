package com.javastarterkit.batch.processor;

import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

import com.javastarterkit.batch.entity.UserRecord;

@Component
public class UserProcessor implements ItemProcessor<UserRecord, UserRecord> {

    @Override
    public UserRecord process(UserRecord user) throws Exception {
        // Business logic transformation
        String fullName = user.getFirstName() + " " + user.getLastName();
        user.setEmail(fullName.toLowerCase().replace(" ", ".") + "@example.com");
        return user;
    }
}