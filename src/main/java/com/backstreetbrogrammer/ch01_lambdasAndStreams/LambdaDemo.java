package com.backstreetbrogrammer.ch01_lambdasAndStreams;

import java.util.Comparator;
import java.util.Locale;
import java.util.function.Consumer;
import java.util.function.Supplier;

public class LambdaDemo {

    public static void main(final String[] args) {
        // Supplier
        final Supplier<String> supplier = () -> "Hello Students!!";
        System.out.println(supplier.get());

        // Consumer
        final Consumer<String> consumer = (String s) -> { // need to put in curly braces if more than 1 statements
            System.out.println(s.toUpperCase(Locale.ROOT));
            System.out.println(s.toLowerCase(Locale.ROOT));
        };
        consumer.accept(supplier.get());

        final Comparator<Integer> cmp = (i1, i2) -> Integer.compare(i1, i2);
        final int compared = cmp.compare(5, 10);
    }

}
