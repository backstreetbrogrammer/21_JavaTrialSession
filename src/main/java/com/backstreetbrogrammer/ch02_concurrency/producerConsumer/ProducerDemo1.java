package com.backstreetbrogrammer.ch02_concurrency.producerConsumer;

public class ProducerDemo1<T> {
    private final T[] buffer;
    private final int bufferSize;
    private int count = 0;

    public ProducerDemo1(final T[] buffer) {
        if (buffer == null || buffer.length == 0) {
            throw new IllegalArgumentException();
        }
        this.buffer = buffer;
        this.bufferSize = buffer.length;
    }

    public void produce(final T item) {
        while (isFull(buffer)) {
            // wait
        }
        buffer[count++] = item;
    }

    private boolean isFull(final T[] buffer) {
        return count >= (bufferSize - 1);
    }

}
