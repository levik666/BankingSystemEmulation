package queue;

import queue.service.Producer;
import queue.service.impl.BlockingQueue;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ApplicationExecutor {

    public static void main(String[] args) {
        final BlockingQueue<String> blockingQueue = new BlockingQueue<>(2);
        final ExecutorService executorService = Executors.newFixedThreadPool(3);

        executorService.submit(new Producer(blockingQueue));
        executorService.submit(new queue.service.Consumer(blockingQueue));
    }
}
