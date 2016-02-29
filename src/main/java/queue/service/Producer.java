package queue.service;

import queue.service.impl.BlockingQueue;

import java.util.Random;

public class Producer implements Runnable{

    private static final long SLEEP = 1000l;

    private final BlockingQueue<String> queue;

    public Producer(final BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while(true) {
            final String element = String.valueOf(new Random().nextInt());
            try {
                queue.put(element);
                System.out.println("Publish element " + element + ", by thread " + Thread.currentThread().getName());

                Thread.sleep(SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

    }
}
