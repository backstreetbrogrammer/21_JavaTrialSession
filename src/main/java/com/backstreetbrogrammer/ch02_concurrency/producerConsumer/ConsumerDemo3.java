package com.backstreetbrogrammer.ch02_concurrency.producerConsumer;

public class ConsumerDemo3<T> {
    private final T[] buffer;
    private final int bufferSize;
    private final Object lock;
    private int count = 0;

    public ConsumerDemo3(final T[] buffer, final Object lock) {
        if (buffer == null || buffer.length == 0) {
            throw new IllegalArgumentException();
        }
        this.buffer = buffer;
        this.bufferSize = buffer.length;
        this.lock = lock;
    }

    public T consume() {
        synchronized (lock) {
            while (isEmpty(buffer)) {
                // wait
            }
            return buffer[--count];
        }
    }

    private boolean isEmpty(final T[] buffer) {
        return count == 0;
    }

}
