package com.javastarterkit.kafka.entity;

public class MessageEvent {

    private String id;
    private String content;
    private String source;

    public MessageEvent() {
    }

    public MessageEvent(String id, String content, String source) {
        this.id = id;
        this.content = content;
        this.source = source;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}