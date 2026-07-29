// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.batch.processor;

import com.javastarterkit.batch.entity.UserRecord;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.stereotype.Component;

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
