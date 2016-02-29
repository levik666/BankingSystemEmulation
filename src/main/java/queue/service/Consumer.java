package queue.service;

import queue.service.impl.BlockingQueue;

public class Consumer implements Runnable {

    private static final long SLEEP = 1000l;

    private final BlockingQueue<String> queue;

    public Consumer(final BlockingQueue<String> queue) {
        this.queue = queue;
    }

    @Override
    public void run() {
        while (true) {
            try {
                final String element = queue.tack();
                System.out.println("Consumed element " + element + ", by thread " + Thread.currentThread().getName());
                Thread.sleep(SLEEP);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
