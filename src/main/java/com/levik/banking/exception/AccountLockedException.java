package com.levik.banking.exception;

public class AccountLockedException extends Exception {

    public AccountLockedException(String message) {
        super(message);
    }
}
