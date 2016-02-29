package queue.service.impl;

import queue.service.Queue;

import java.util.LinkedList;
import java.util.List;

public class BlockingQueue<E> implements Queue<E> {

    private List<E> list = new LinkedList<>();
    private int size;


    public BlockingQueue(final int size) {
        this.size = size;
    }

    @Override
    public void put(E element) throws InterruptedException {
        synchronized(list) {
            if (list.size() == size) {
                list.wait();
            }
            list.add(element);
            list.notifyAll();
        }
    }

    @Override
    public E tack() throws InterruptedException {
        synchronized(list) {
            if (list.isEmpty()) {
                list.wait();
            }

            final E element = list.get(list.size() - 1);
            list.notifyAll();
            return element;
        }
    }

}
