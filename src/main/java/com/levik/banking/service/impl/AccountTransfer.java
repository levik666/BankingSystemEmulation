package com.levik.banking.service.impl;

import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import com.levik.banking.exception.InsufficientFundsException;
import com.levik.banking.exception.TransferFailed;
import com.levik.banking.model.Account;
import com.levik.banking.model.TransferResult;

public class AccountTransfer implements Callable<TransferResult> {

    private static final int WAIT_SEC = 2;
    private static final long INTERVAL_SLEEP = 5;

    private final Account from;
    private final Account to;
    private final int transferAmount;

    private TransferResult transferResult;

    public AccountTransfer(final Account from, final Account to, final int transferAmount) {
        this.from = from;
        this.to = to;
        this.transferAmount = transferAmount;

        transferResult = new TransferResult(from, to, transferAmount);
    }

    public Account getFrom() {
        return from;
    }

    public Account getTo() {
        return to;
    }

    public int getTransferAmount() {
        return transferAmount;
    }

    public TransferResult call() throws Exception {
        System.out.println("Transfer started from " + from.getId() + " to " + to.getId()+ " with amount " + transferAmount + " Thread name " + Thread.currentThread().getName());

        if (from.getId() > to.getId()) {
            return perform(from, to);
        } else if (from.getId() < to.getId()) {
            return perform(to, from);
        }

        transferResult.setDone(true);
        return transferResult;
    }

    private TransferResult perform(final Account from, final Account to){
        try{
            if (from.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                try {

                    if (from.getBalance() < transferAmount) {
                        throw new InsufficientFundsException();
                    }

                    System.out.println("Looked from " + from.getId() +  " by thread " + Thread.currentThread().getName());
                    Thread.sleep(INTERVAL_SLEEP);

                    if (to.getLock().tryLock(WAIT_SEC, TimeUnit.SECONDS)) {
                        try {
                            System.out.println("Looked to " + to.getId() + " by thread " + Thread.currentThread().getName());
                            Thread.sleep(INTERVAL_SLEEP);
                            if (from.equals(to)) {
                                System.out.println("Transfer not needed due to firstAccount and secondAccount the same");
                            }
                            from.withdraw(transferAmount);
                            to.deposit(transferAmount);
                            System.out.println("Transfer from " + from.getId() + " to " + to.getId() + " completed" + ". by thread " + Thread.currentThread().getName());
                        } finally {
                            to.getLock().unlock();
                        }
                    } else {
                        final String errorMessage = "Transfer from " + from.getId() + " to " + to.getId() + " failed by thread " + Thread.currentThread().getId() + ". It will be performed later.";
                        System.err.println(errorMessage);
                        transferResult.setThrowable(new TransferFailed(errorMessage));
                        transferResult.setDone(false);
                        return transferResult;
                    }
                } finally {
                    from.getLock().unlock();
                }
            } else {
                final String errorMessage = "Transfer from " + from.getId() + " to " + to.getId() + " failed by thread " + Thread.currentThread().getId();
                System.err.println(errorMessage);
                transferResult.setThrowable(new TransferFailed(errorMessage));
                transferResult.setDone(false);
                return transferResult;
            }
        } catch (InterruptedException | InsufficientFundsException exe) {
            final String errorMessage = "Can't make transfer from " + from.getId() + ", to " + to.getId() + " with amount " + transferAmount + " failed by thread " + Thread.currentThread().getId();
            System.err.println(errorMessage);
            transferResult.setThrowable(new TransferFailed(errorMessage));
            transferResult.setDone(false);
            return transferResult;
        }

        transferResult.setDone(true);
        return transferResult;
    }
}
