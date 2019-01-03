# Functional Core / Imperative Shell

This is an example application implementing the architectural style `Functional Core / Imperative Shell` in Java.

This style populated by Gary Bernhardt is quite good known in the ruby community in the meantime. The motivation of this project is to bring this style into the world of Java.

The idea of this style is to separate code that is side effect free from code that has side effects. Gary Bernhardt states that functional code contains the `Paths` whereas the `imperative shell` contains the `Dependencies`

## Functional Core

The `functional core` is made up of logic that follows a functional programming paradigm. Especially the concept of `pure functions`.

A `pure function` has the following properties (taken from en.wikipedia.org):
- Its return value is the same for the same arguments (no variation with local static variables, non-local variables, mutable reference arguments or input streams from I/O devices).
- Its evaluation has no side effects (no mutation of local static variables, non-local variables, mutable reference arguments or I/O streams).

Values passed in or out to `core` methods are immutable. Mutable objects may be used within methods. But they must never be passed to other methods or returned to the caller.

**Core code is free from dependencies to the shell.** The only way to pass values from the `core` to the `shell` is by using return values!

### FauxO

Gary Bernhardt combines some characteristics of object oriented programming with functional programming. He calls this combination `FauxO`.

`FauxO` means that a class may contain both, data and code. However, compared to object orientation, all parameters into the code and the return values have to be immutable.

An example of `FauxO` within this project is the class `PlayerList`. It contains state (e.g. a list of players) and code (it is possible to add a new player). However the `PlayerList` that is returned is a new instance - The PlayerList itself is immutable.

### Readability of "Core Code"

Values don't change.  
You can call functions and know that no internal state has been changed. You just pass values in and get a result out. Because the objects are immutable, they cannot be changed by different threads.

### Debuggability of "Core Code"

Values don't change.  
You don't find yourself figuring out at which place of the code a certain value has been mutated. Objects are immutable, so it is easy to follow the methods that created that object.

### Testability of "Core Code"

Values don't change.  
Methods with the same input parameters always return the same result. Therefore the methods are easily testable in isolation making the need of mocking (test doubles) at least extremely rare.
Tests within the `core` are Unit Tests. There is no mocking in place, making the test code much more concise.

## Imperative Shell

An application without side effects wouldn't make much sense. There is some HTTP traffic going on, something that should be presented to the user or some inputs from the user that shall be stored into a persistent data store. Such functionality is covered by the `imperative shell`. It is a wrapper of the `functional core`.

Making decisions is usually part of the `core` code. The `shell` is "just" the interface to components that cause side effects. Therefore there should be really rare cases for loops and conditionals within the `shell` code. 

In my first attempts it was hard to find ways that would avoid conditionals in the `shell` code. I really had to rethink my coding styles. I was tempted to return some kind of intermediate results to the `shell`.
An example is the information whether the player creation was successful or not (The class `PlayerList` should initially return a boolean value of `true` or `false`). The `shell` would have to decide which message shall be shown depending on the outcome. However, this can be perfectly done within the `core`. The `core` defines the message for the user directly as a result of the user creation operation. This is fine as the resulting message can be seen as `core` (and even domain) logic. The `shell` just knows where to display the result message on the UI. 

Therefore, you should be aware of solutions that avoid decisions in the `shell` code.  

### Readability of "Shell Code"

`Shell` code is quite linear.  
Ideally the `shell` code doesn't contain any conditions. Decisions are mainly made within the `functional core`.

### Debuggability "Shell Code"

`Shell` code is quite linear.  
The flow of the instructions in the `shell` code are quite clear. The `shell` just provides the input data and further processes the output data that has been calculated within the `core`. Mutation of objects (like the references of the `ApplicationState`) just occur in this linear path. 

### Testability "Shell Code"

`Shell` code is quite linear.  
There are only a few test cases for a `shell` artifact because most of the functional logic is already tested within the `core`. Tests of the `shell` code "just" checks the integration of the `functional core` and the infrastructure.
Tests within the `shell` are Integration Tests - checking the connectivity of the `shell` to the infrastructure. It may happen that mock implementations are used here (like H2 instead of a persistent database ore MockMvC instead of real Browser calls). Therefore additional smoke tests using the real infrastructure are usually still inevitable.

## Shades of grey

### Side effects
Its within the own definition of placing some edge cases of functionality within the `core` although they are not side effect free. An example that may be quite easy to understand is logging. strictly speaking logging is a side effect (putting information on stdout). So, the ideal way would be to pass all the log messages back to the imperative `shell`. However, these side effects usually don't affect your application, may also hold a lot of information (timestamp, correlation id, ...) and probably you wouldn't write tests for proper logging. So there may be some rare cases, where it is up to the developer team to decide how strict you are.

### Referential Transparency / Exceptions and Exception Handling
https://stackoverflow.com/questions/10703232/why-is-the-raising-of-an-exception-a-side-effect

### Shell or Core?
Sometimes you may find it hard to define that a specific logic is `core` or `shell` code. In the project we startet to put all the domain logic into the `core`. This is definitely alright.
However sometimes logic is needed that doesn't really belong to the business domain. The rule of thumb that we used over time is not only to check against whether it is domain logic but to check whether the logic has side effects.
It's fair enough to have several `core` packages like `business`, `authentication` and others as long as the code in it is side effect free. 

## Recommended References

There is a [Gist by kbilsted](https://gist.github.com/kbilsted/abdc017858cad68c3e7926b03646554e) with a quite exhaustive link list:

Especially recommendable are the references by Gary Bernhardt:  
[Destroy all software screencast](https://www.destroyallsoftware.com/screencasts/catalog/functional-core-imperative-shell)  
[Presentation at Ruby Conf 2012](https://www.youtube.com/watch?v=yTkzNHF6rMs)
  
## Additional information

### Other architectural styles (probably more known in Java)

[Layered Architecture](https://medium.com/code-smells/layered-architecture-f11bc04c5d6c)  
[Hexagonal Architecture](https://fideloper.com/hexagonal-architecture)  
[Clean Architecture](http://blog.cleancoder.com/uncle-bob/2012/08/13/the-clean-architecture.html)  
[IODA Architecture](http://blog.ralfw.de/2015/04/die-ioda-architektur.html)  
[Sliced Architecture](https://jimmybogard.com/vertical-slice-architecture/)

### Appropriateness of Java

Although Java contains some immutable objects (e.g. String, LocalDateTime) it is not defined with immutability in mind. In Java it may be cumbersome to declare private constructors or copy constructors with each data holding class. An additional example is working with immutable collections

#### Java

```java
        Set<Player> modifiableSet = new HashSet<>(players);
        modifiableSet.add(new Player(newPlayerName));
        return Collections.unmodifiableSet(modifiableSet);
```

#### Java with Guava

Some support is given by Guava. But still it isn't quite handy:

```java
Set<Player> newSet = new ImmutableSet.Builder<Player>()
                                .addAll(players)
                                .add(newPlayerName)
                                .build();
```

Quite the same functionality is also provided by Apache Commons

#### Java with Vavr

#### Kotlin

Kotlin would automatically return a new list just by using the `+` operator:

```kotlin
return players + newPlayerName
```
