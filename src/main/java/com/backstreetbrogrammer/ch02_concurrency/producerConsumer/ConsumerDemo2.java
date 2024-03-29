package com.backstreetbrogrammer.ch02_concurrency.producerConsumer;

public class ConsumerDemo2<T> {
    private final T[] buffer;
    private int count = 0;

    public ConsumerDemo2(final T[] buffer) {
        if (buffer == null || buffer.length == 0) {
            throw new IllegalArgumentException();
        }
        this.buffer = buffer;
    }

    public synchronized T consume() {
        while (isEmpty()) {
            // wait
        }
        return buffer[--count];
    }

    private boolean isEmpty() {
        return count == 0;
    }

}
