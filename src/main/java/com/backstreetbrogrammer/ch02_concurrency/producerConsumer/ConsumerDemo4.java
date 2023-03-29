package com.backstreetbrogrammer.ch02_concurrency.producerConsumer;

public class ConsumerDemo4<T> {
    private final T[] buffer;
    private final int bufferSize;
    private final Object lock;
    private int count = 0;

    public ConsumerDemo4(final T[] buffer, final Object lock) {
        if (buffer == null || buffer.length == 0) {
            throw new IllegalArgumentException();
        }
        this.buffer = buffer;
        this.bufferSize = buffer.length;
        this.lock = lock;
    }

    public T consume() throws InterruptedException {
        synchronized (lock) {
            try {
                while (isEmpty(buffer)) {
                    lock.wait();
                }
                return buffer[--count];
            } finally {
                lock.notifyAll();
            }
        }
    }

    private boolean isEmpty(final T[] buffer) {
        return count == 0;
    }

}
