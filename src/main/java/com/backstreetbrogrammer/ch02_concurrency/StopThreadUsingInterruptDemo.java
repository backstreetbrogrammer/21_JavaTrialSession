package com.backstreetbrogrammer.ch02_concurrency;

import java.util.concurrent.TimeUnit;

public class StopThreadUsingInterruptDemo implements Runnable {
    @Override
    public void run() {
        while (!Thread.currentThread().isInterrupted()) {
            try {
                TimeUnit.MILLISECONDS.sleep(1L);
            } catch (final InterruptedException e) {
                e.printStackTrace();
            }
            // do the thread task
        }
    }

}
