// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.ioc.service;

import com.javastarterkit.ioc.model.Message;

/**
 * Service interface for message operations.
 */
public interface MessageService {
    String processMessage(Message message);
}
