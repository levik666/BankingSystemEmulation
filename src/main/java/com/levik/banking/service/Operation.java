package com.levik.banking.service;

import com.levik.banking.exception.AccountLockedException;
import com.levik.banking.exception.InsufficientFundsException;
import com.levik.banking.model.Account;

public interface Operation {

    void transfer(Account firstAccount, Account secondAccount, int amount) throws InsufficientFundsException, InterruptedException, AccountLockedException;
}
