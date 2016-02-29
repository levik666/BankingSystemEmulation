package com.levik.banking.exception;

public class TransferFailed extends RuntimeException{

    public TransferFailed(final String message) {
        super(message);
    }

    public TransferFailed(final String message, final Throwable cause) {
        super(message, cause);
    }
}
