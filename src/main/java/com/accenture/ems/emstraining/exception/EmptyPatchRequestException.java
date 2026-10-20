package com.accenture.ems.emstraining.exception;

public class EmptyPatchRequestException extends RuntimeException {
    public EmptyPatchRequestException(String message) {
        super(message);
    }
}