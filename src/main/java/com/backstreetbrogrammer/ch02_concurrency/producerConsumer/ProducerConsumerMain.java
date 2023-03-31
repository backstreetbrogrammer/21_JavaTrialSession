package com.backstreetbrogrammer.ch02_concurrency.producerConsumer;

public class ProducerConsumerMain {

    private static final Object lock = new Object();

    private static int[] buffer;
    private static int count;

    private static class Producer {
        void produce() {
            synchronized (lock) {
                if (isFull(buffer)) {
                    try {
                        lock.wait();
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                buffer[count++] = 1;
                lock.notifyAll();
            }
        }
    }

    private static class Consumer {
        void consume() {
            synchronized (lock) {
                if (isEmpty()) {
                    try {
                        lock.wait();
                    } catch (final InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                buffer[--count] = 0;
                lock.notifyAll();
            }
        }
    }

    private static boolean isEmpty() {
        return count == 0;
    }

    private static boolean isFull(final int[] buffer) {
        return count == buffer.length;
    }

    public static void main(final String... strings) throws InterruptedException {
        buffer = new int[10];
        count = 0;

        final Producer producer = new Producer();
        final Consumer consumer = new Consumer();

        final Runnable produceTask = () -> {
            for (int i = 0; i < 30; i++) {
                producer.produce();
            }
            System.out.println("Done producing");
        };

        final Runnable consumeTask = () -> {
            for (int i = 0; i < 25; i++) {
                consumer.consume();
            }
            System.out.println("Done consuming");
        };

        final Thread consumerThread = new Thread(consumeTask);
        final Thread producerThread = new Thread(produceTask);

        consumerThread.start();
        producerThread.start();

        consumerThread.join();
        producerThread.join();

        System.out.printf("Data in the buffer: %d%n", count);
    }

}
