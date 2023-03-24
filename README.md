# Java Trial Session

> This is a Java free trial session covering lambdas, streams and concurrency.

Tools used:

- JDK 11
- Maven
- JUnit 5, Mockito
- IntelliJ IDE

## Table of contents

1. Lambda and Streams
    - Lambda Expressions and Functional Interfaces
    - Exploring `java.util.function` package
2. Concurrency
    - Threading fundamentals - creation and coordination
    - Thread pools
    - Concurrent Collections
    - Synchronization techniques - Locks and Conditions

---

### Chapter 01. Lambdas and Streams

#### Lambda Expressions and Functional Interfaces

Lambda expressions basically express instances of **functional interfaces**.

What is a **Functional interface**?

An interface with **single** abstract method is called functional interface.

- `default` and `static` methods in interface do **NOT** count.
- methods from `java.lang.Object` do **NOT** count (`equals()`, `toString()`, `hashCode()`, etc.)

An example is `java.lang.Runnable`.

```java
package java.lang;

@FunctionalInterface
public interface Runnable {
    void run();
}
```

Lambda expressions implement the only abstract function and therefore implement functional interfaces.

Lambda expressions are added in Java 8 and provide below functionalities:

- Enable to treat functionality as a **method argument**, or code as **data**.
- A function that can be created without belonging to any class.
- A lambda expression can be passed around as if it was an object and executed on demand.

**Example:**

Implement a functional interface.

```java

@FunctionalInterface
public interface Supplier<T> {
    T get();
}
```

```
Supplier<String> supplier = () -> "Hello Students!!";
```

Complete source code:

```java
import java.util.function.Supplier;

public class LambdaDemo {

    public static void main(final String[] args) {
        final Supplier<String> supplier = () -> "Hello Students!!";
        System.out.println(supplier.get());
    }

}
```

Output:

```
Hello Students!!
```

Another example using **Consumer**:

```java

@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);

    default Consumer<T> andThen(final Consumer<? super T> after) {
        Objects.requireNonNull(after);
        return (T t) -> {
            accept(t);
            after.accept(t);
        };
    }
}
```

Complete source code:

```java
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
    }

}
```

Output:

```
Hello Students!!
HELLO STUDENTS!!
hello students!!
```

![Lambda Syntax](LambdaSyntax.PNG)

#### Exploring `java.util.function` package

JDK has more than 40 interfaces in `java.util.function` package organized in 4 categories:

- Supplier
- Consumer
- Predicate
- Function

#### Supplier

The `Supplier`

- does not take any argument
- produces a value

```java
public interface Supplier<T> {
    T get();
}
```

```
Supplier<String> supplier = () -> "Hello";
```

#### Consumer

The `Consumer`

- takes any argument
- does not return anything

```java
public interface Consumer<T> {
    void accept(T t);
}
```

```
Consumer<String> consumer = s -> System.out.println(s);
```

#### Predicate

The `Predicate`

- takes any argument
- returns a **boolean**

Used to filter data.

```java
public interface Predicate<T> {
    boolean test(T t);
}
```

```
Predicate<String> isEmpty = s -> s.isEmpty();
```

#### Function

The `Function`

- takes any argument
- returns any type

Used to map data.

```java
public interface Function<T, R> {
    R apply(T t);
}
```

```
Function<Student, String> getStudentId = student -> student.getId();
```

#### Runnable

Although `Runnable` interface lies in `java.lang` package, it is still a functional interface. Thus, any interface which
has ONLY ONE abstract method is always a functional interface and annotating with `@FunctionalInterface` is optional.

The `Runnable`

- does not take any argument
- does not return anything

Used for defining thread task.

```java
public interface Runnable {
    void run();
}
```

```
Runnable runMe = () -> System.out.println("I am running in a separate thread");
```

#### Interview Problem 1 (Societe Generale): Demonstrate functional interfaces in code

Given a **Java POJO**:

```java
public class Student {

    private String name;

    public Student(final String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(final String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                '}';
    }

}
```

1. Print all students using `Consumer`
2. Print all students names using `Function` and `Consumer`
3. Print all students names starting with **'T'** using `Predicate` and `Consumer`

**Solution**:

```java
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
```

**Output**:

```
1. Print all students using Consumer~>
Student{name='John'}
Student{name='Mary'}
Student{name='Thomas'}
Student{name='Rahul'}
Student{name='Jenny'}
Student{name='Tatiana'}
----------------------
2. Print all students names using Function and Consumer~>
John
Mary
Thomas
Rahul
Jenny
Tatiana
----------------------
3. Print all students names starting with 'T' using Predicate and Consumer~>
Thomas
Tatiana
----------------------
```

