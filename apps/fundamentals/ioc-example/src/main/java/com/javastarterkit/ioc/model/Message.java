// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.ioc.model;

/**
 * Immutable model class representing a message.
 */
public class Message {
    private final String content;
    private final String sender;

    public Message() {
        this.content = null;
        this.sender = null;
    }

    public Message(String content, String sender) {
        this.content = content;
        this.sender = sender;
    }

    public String getContent() {
        return content;
    }

    public String getSender() {
        return sender;
    }

    @Override
    public String toString() {
        return "Message{" + "content='" + content + '\'' + ", sender='" + sender + '\'' + '}';
    }
}
