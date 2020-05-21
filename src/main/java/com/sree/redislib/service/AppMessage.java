package com.sree.redislib.service;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix="service")
public class AppMessage {
    private String message;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
