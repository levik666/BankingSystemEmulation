package com.levik.banking.model;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Account {

    private Lock lock;

    private int balance;
    private int id;

    public Account(final int balance, final int id) {
        this.balance = balance;
        this.id = id;

        lock = new ReentrantLock();

        System.out.println(this);
    }

    public int getId() {
        return id;
    }

    @Override
    public boolean equals(final Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        final Account account = (Account) o;

        if (balance != account.balance) return false;
        return id == account.id;

    }

    @Override
    public int hashCode() {
        int result = balance;
        result = 31 * result + id;
        return result;
    }

    public void withdraw(int amount){
        balance -= amount;
    }

    public void deposit(int amount){
        balance += amount;
    }

    public int getBalance() {
        return balance;
    }

    public Lock getLock() {
        return lock;
    }

    @Override
    public String toString() {
        return "Account with id " + id + " and balance " + balance;
    }
}
