package com.levik.banking;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.*;

import com.levik.banking.model.Account;
import com.levik.banking.model.TransferResult;
import com.levik.banking.service.Consumer;
import com.levik.banking.service.Producer;
import com.levik.banking.service.impl.AccountTransfer;

public class ApplicationExecutor {

    public static void main(String[] args) throws ExecutionException, InterruptedException {
        System.out.println("Banking Application started");

        final ExecutorService executorService = Executors.newCachedThreadPool();
        final SynchronousQueue<TransferResult> queue = new SynchronousQueue<>(true);

        final Account account1 = new Account(100, 457);
        final Account account2 = new Account(200, 789);
        final Account account3 = new Account(300, 963);
        final Account account4 = new Account(400, 741);

        final int transferAmount = new Random().nextInt(10);

        final Future<TransferResult> submit = executorService.submit(new AccountTransfer(account1, account2, transferAmount));
        final Future<TransferResult> submit1 = executorService.submit(new AccountTransfer(account2, account1, transferAmount));

        final List<Future<TransferResult>> futures = Arrays.asList(submit, submit1/*, submit2, submit3*/);

        executorService.submit(new Producer(queue, futures));
        executorService.submit(new Consumer(queue));

        //executorService.awaitTermination(1, TimeUnit.MINUTES);
    }
}
