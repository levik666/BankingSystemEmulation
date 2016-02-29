package com.levik.banking.service;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.SynchronousQueue;

import com.levik.banking.model.TransferResult;
import com.levik.banking.service.impl.AccountTransfer;

public class Consumer implements Runnable {

    private final static int retryCount = 3;

    private SynchronousQueue<TransferResult> queue;

    public Consumer(final SynchronousQueue<TransferResult> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        int count = 0;

        while(true) {
            try {
                System.out.println("Retry started by thread " + Thread.currentThread().getName());
                System.out.println("Get fail queue size in retry " + queue.size() + " by thread " + Thread.currentThread
                        ().getName());
                final TransferResult transferResult = queue.take();
                if (transferResult != null) {
                    System.out.println("Get fail queue size in retry " + queue.size() + " by thread " + Thread
                            .currentThread().getId());
                    System.out.println("Get fail transfer [ " + transferResult.getFrom().getId() + ", " + transferResult
                            .getTo().getId() + " ] -> " + transferResult.getTransferAmount() + " from queue to retry by " +
                            "thread " + Thread.currentThread().getName());

                    final AccountTransfer accountTransfer = new AccountTransfer(transferResult.getFrom(), transferResult
                            .getTo(), transferResult.getTransferAmount());

                    while (count <= retryCount) {
                        final TransferResult call = accountTransfer.call();
                        if (call.isDone()) {
                            System.out.println("Retry done " + accountTransfer.getFrom().getId() + " to " +
                                    accountTransfer.getTo().getId() + " with amount " + accountTransfer.getTransferAmount
                                    () + " by thread " + Thread.currentThread().getName() + " queue size " + queue.size());
                            return;
                        } else {
                            System.out.println("Retry failed try more " + accountTransfer.getFrom().getId() + " to " +
                                    accountTransfer.getTo().getId() + " with amount " + accountTransfer.getTransferAmount
                                    () + " by thread " + Thread.currentThread().getName() + " current count " + count + " queue size " + queue.size());
                            count++;
                        }
                    }

                    System.err.println("Can't retry give up " + accountTransfer.getFrom().getId() + " to " +
                            accountTransfer.getTo().getId() + " with amount " + accountTransfer.getTransferAmount
                            () + " by thread " + Thread.currentThread().getName() + " queue size " + queue.size());
                }
            } catch (Exception exe) {
                exe.printStackTrace();
            }
        }
    }
}
