package com.backstreetbrogrammer.ch02_concurrency.producerConsumer;

public class ConsumerDemo1<T> {
    private final T[] buffer;
    private final int bufferSize;
    private int count = 0;

    public ConsumerDemo1(final T[] buffer) {
        if (buffer == null || buffer.length == 0) {
            throw new IllegalArgumentException();
        }
        this.buffer = buffer;
        this.bufferSize = buffer.length;
    }

    public T consume() {
        while (isEmpty(buffer)) {
            // wait
        }
        return buffer[--count];
    }

    private boolean isEmpty(final T[] buffer) {
        return count == 0;
    }

}
