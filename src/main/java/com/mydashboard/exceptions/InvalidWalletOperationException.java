package com.mydashboard.exceptions;

public class InvalidWalletOperationException extends RuntimeException {
    public InvalidWalletOperationException(String message) {
        super(message);
    }
}

