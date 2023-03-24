package com.backstreetbrogrammer.ch01_lambdasAndStreams;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public class FunctionalInterfacesDemo {

    public static void main(final String[] args) {
        final var john = new Student("John");
        final var mary = new Student("Mary");
        final var thomas = new Student("Thomas");
        final var rahul = new Student("Rahul");
        final var jenny = new Student("Jenny");
        final var tatiana = new Student("Tatiana");

        final var students = List.of(john, mary, thomas, rahul, jenny, tatiana);
        System.out.println("1. Print all students using Consumer~>");
        // Consumer
        students.forEach(student -> System.out.println(student));
        System.out.println("----------------------");

        final List<String> names = new ArrayList<>();

        // Function
        final Function<Student, String> toName = (Student student) -> student.getName();

        // Consumer
        students.forEach(student -> {
            final String name = toName.apply(student); // Function mapping
            names.add(name);
        });

        System.out.println("2. Print all students names using Function and Consumer~>");
        names.forEach(name -> System.out.println(name));
        System.out.println("----------------------");

        // Predicate
        final Predicate<String> startsWithT = name -> !name.startsWith("T");
        names.removeIf(startsWithT);
        // OR,
        // names.removeIf(name -> !name.startsWith("T")); // inline

        System.out.println("3. Print all students names starting with 'T' using Predicate and Consumer~>");
        // Consumer
        names.forEach(name -> System.out.println(name));
        System.out.println("----------------------");
    }

}
