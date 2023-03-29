package com.backstreetbrogrammer.ch02_concurrency.producerConsumer;

public class ProducerDemo2<T> {
    private final T[] buffer;
    private final int bufferSize;
    private int count = 0;

    public ProducerDemo2(final T[] buffer) {
        if (buffer == null || buffer.length == 0) {
            throw new IllegalArgumentException();
        }
        this.buffer = buffer;
        this.bufferSize = buffer.length;
    }

    public synchronized void produce(final T item) {
        while (isFull(buffer)) {
            // wait
        }
        buffer[count++] = item;
    }

    private boolean isFull(final T[] buffer) {
        return count >= (bufferSize - 1);
    }

}
