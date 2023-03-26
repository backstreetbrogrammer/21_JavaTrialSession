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
    - Lambdas vs Anonymous classes
    - Chaining and Composing Lambdas
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

#### Lamdas vs Anonymous classes

Prior to Java 8, the primary means of creating a **function object** was the **anonymous class**.

Code Snippet to sort a list of strings in order of length using an anonymous class to create the sort's comparison
function (which imposes the sort order):

```
        Collections.sort(words, new Comparator<String>() {
            @Override
            public int compare(final String s1, final String s2) {
                return Integer.compare(s1.length(), s2.length());
            }
        });
```

Lambdas are similar in function to anonymous class, but far more concise.

```
        Collections.sort(words, (s1, s2) -> Integer.compare(s1.length(), s2.length()));
        // OR
        Collections.sort(words, Comparator.comparingInt(String::length));
        // OR
        words.sort(Comparator.comparingInt(String::length));
```

The most important difference is in the **performance** => lambdas are more than **60 times faster** than anonymous
classes!

The reason is the way the Java compiler compiles the lambdas versus the anonymous classes.

Java compiler uses a special `invokedynamic` call to compile lambdas and thus the compiled code is different and much
faster.

Other reason is automatic **"boxing"** and **"unboxing"** of primitives and their wrapper classes.

For ex:

```
        Comparator<Integer> cmp = (i1, i2) -> Integer.compare(i1, i2);
        int compared = cmp.compare(5, 10);
```

Comparator interface:

```java
public interface Comparator<T> {
    int compare(T o1, T o2);
}
```

So above code will be interpreted as when we call compare method: `int compared = cmp.compare(5, 10);`

```java
public interface Comparator<Integer> {
    int compare(Integer o1, Integer o2);
}
```

Firstly, **boxing** will be done to promote primitive `int` 5 and 10 to `Integer`.

After the comparison, resulting `Integer` (a negative integer, zero, or a positive integer as the first argument is less
than, equal to, or greater than the second) will be converted back or **unboxed** to primitive `int`.

If this code is called on millions of integers => this will badly impact the performance.

Thus, to overcome this - a set of functional interfaces are added for primitive types which can be used by lambdas, for
ex:

- `IntPredicate`
- `LongSupplier`
- `IntFunction<T>`
- `LongToIntFunction`

Just taking an example again, in the `Supplier` interface, we have a generic type `T` returned via `get()` method.

```java
public interface Supplier<T> {
    T get();
}
```

However, for `LongSupplier` interface, we can directly get primitive `long` via `getAsLong()` method as return value
without any need to **box** it to wrapper `Long`.

```java
public interface LongSupplier {
    long getAsLong();
}
```

Similarly, for `DoubleToIntFunction` interface, we can directly work with primitives to create the function to
convert `double` to `int`.

```java
public interface DoubleToIntFunction {
    int applyAsInt(double value);
}
```

Complete Java code example to illustrate this:

```java
import java.util.function.DoubleToIntFunction;
import java.util.function.LongSupplier;

public class PrimitiveLambdas {

    public static void main(final String[] args) {
        final LongSupplier supplier = () -> 10L;
        final long i = supplier.getAsLong();
        System.out.printf("i = %d%n", i);

        final DoubleToIntFunction function = value -> (int) Math.ceil(value);
        final int pi = function.applyAsInt(Math.PI);
        System.out.printf("PI = %d%n", pi);
    }

}
```

**Output**:

```
i = 10
PI = 4
```

#### Chaining and Composing Lambdas


