package com.backstreetbrogrammer.ch02_concurrency;

public class SingletonDemo {

    private static SingletonDemo instance;

    private SingletonDemo() {
    }

    public static synchronized SingletonDemo getInstance() {
        if (instance == null) {
            instance = new SingletonDemo();
        }
        return instance;
    }

}
