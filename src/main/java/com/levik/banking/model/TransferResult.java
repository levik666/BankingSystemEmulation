package com.levik.banking.model;

public class TransferResult {

    private final Account from;
    private final Account to;
    private final int transferAmount;
    private boolean isDone;
    private Throwable throwable;

    public TransferResult(final Account from, final Account to, final int transferAmount) {
        this.from = from;
        this.to = to;
        this.transferAmount = transferAmount;
    }

    public Throwable getThrowable() {
        return throwable;
    }

    public void setThrowable(final Throwable throwable) {
        this.throwable = throwable;
    }

    public boolean isDone() {
        return isDone;
    }

    public void setDone(final boolean done) {
        isDone = done;
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
}
