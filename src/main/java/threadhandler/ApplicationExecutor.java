package threadhandler;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;

public class ApplicationExecutor {

    public static void main(String[] args) throws InterruptedException {
        ExecutorService executor = Executors.newCachedThreadPool(new CaughtExceptionsThreadFactory());

        System.out.println("Executing tasks:");
        for (int i = 0; i < 5; ++i) {
            executor.execute(new Worker());
        }

        System.out.println("Shutting down the executor.");
        executor.awaitTermination(2, TimeUnit.SECONDS);
    }

    public static class ThreadUncaughtExceptionHandler implements Thread.UncaughtExceptionHandler {

        @Override
        public void uncaughtException(Thread thread, Throwable exe) {
            System.out.println(thread.getName() + " , exe " + exe.getMessage());
            exe.printStackTrace();
        }
    }

    private static class CaughtExceptionsThreadFactory implements ThreadFactory {
        private ThreadUncaughtExceptionHandler handler = new ThreadUncaughtExceptionHandler();


        @Override
        public Thread newThread(final Runnable runnable) {
            Thread t = new Thread(runnable);
            t.setUncaughtExceptionHandler(handler);
            return t;
        }
    }


    public static class Worker implements Runnable {
        private static int nTasks = 0;
        private int taskId = ++nTasks;

        @Override
        public void run() {
            System.out.println("Starting work in thread: " + taskId);
            try {
                TimeUnit.MILLISECONDS.sleep(200);
            } catch (InterruptedException e) {
                //ignore
            }
            if ((taskId % 2) == 0) {
                throw new RuntimeException("An Exception that ends task: " + taskId);
            } else {
                System.out.printf("Task %d finished normally.%n", taskId);
            }
        }
    }
}
