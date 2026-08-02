package com.javastarterkit.patterns.modelviewpresenter.exception;

/**
 * Base exception for MVP pattern.
 */
public class MvpException extends RuntimeException {
    
    public MvpException(String message) {
        super(message);
    }
    
    public MvpException(String message, Throwable cause) {
        super(message, cause);
    }
}