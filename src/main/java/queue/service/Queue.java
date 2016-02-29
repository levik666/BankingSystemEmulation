package queue.service;

public interface Queue<E> {

    void put(E element) throws InterruptedException;

    E tack() throws InterruptedException;
}
