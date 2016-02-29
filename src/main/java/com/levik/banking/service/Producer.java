package com.levik.banking.service;

import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;

import com.levik.banking.model.TransferResult;

public class Producer implements Runnable{

    private final List<Future<TransferResult>> futures;
    private final SynchronousQueue<TransferResult> queue;

    public Producer(final SynchronousQueue<TransferResult> queue, final List<Future<TransferResult>> futures) {
        this.queue = queue;
        this.futures = futures;
    }

    @Override
    public void run() {
        while (true) {
            for(final Future<TransferResult> future : futures) {
                try{
                    final TransferResult transferResult = future.get();

                    if (!transferResult.isDone()) {
                        System.out.println("Put fail transfer [ " + transferResult.getFrom().getId() + ", " + transferResult.getTo().getId() + " ] -> " + transferResult.getTransferAmount() + " to queue for retry by thread " + Thread.currentThread().getName());
                        queue.put(transferResult);
                    }
                } catch (Exception exe) {
                    exe.getStackTrace();
                }
            }
        }
    }
}
