package com.backstreetbrogrammer.ch02_concurrency;

public class Counter {

    private long counter;

    public Counter(final long counter) {
        this.counter = counter;
    }

    public long getCounter() {
        return counter;
    }

    public synchronized void increment() {
        counter += 1L;
    }

}
