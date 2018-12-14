# Functional Core / Imperative Shell

This is an example application implementing the architectural style "Functional Core / Imperative Shell" in Java.

Motivation is to bring this style that is quite good known in the ruby community into the Java world.

The idea of this style is to separate code that is side effect free from code that has side effects.

Code that is free of side effects is better readable, debuggable and testable.

## Functional Core

### Characteristics of the functional core

The functional core is made up of logic that follows a functional programming paradigm. Especially the concept of pure functions.

A pure function has the following properties (taken from en.wikipedia.org):
- Its return value is the same for the same arguments (no variation with local static variables, non-local variables, mutable reference arguments or input streams from I/O devices).
- Its evaluation has no side effects (no mutation of local static variables, non-local variables, mutable reference arguments or I/O streams).

The code of the functional core may have application state. Therefore all the methods within the core are not necessarily pure functions in its definition.

### FauxO

### Readability of "Core Code"

Values don't change. You can call functions and know that no internal state has been changed. You just pass values in and get a result out. Because the objects are immutable, they cannot be changed by different threads.

### Debuggability of "Core Code"

Values don't change. You don't find yourself figuring out at which place of the code a certain value has been mutated. Objects are immutable, so it is easy to follow the methods that created that object.

### Testability of "Core Code"

Values don't change. Methods with the same input parameters always return the same result. Therefore the methods are easily testable in isolation making the need of mocking (test doubles) extremely rare.

## Imperative Shell

### Characteristics of the imperative shell

An application without side effects wouldn't make much sense. There is some HTTP traffic going on, something that should be presented to the user or some inputs from the user that shall be stored into a persistent data store. Therefore the imperative shell is a wrapper of the functional core.

### Readability of "Shell Code"

Shell code is quite linear. Ideally the shell code doesn't contain any conditions. Decisions are mainly made within the functional core.

### Debuggability "Shell Code"

Shell code is quite linear. The flow of the instructions in the shell code are quite clear. The "shell" just provides the input data and processes the output data that has been calculated within the core. 

### Testability "Shell Code"

Shell code is quite linear. Therefore there are only a few test cases for a shell artifact. As most of the functional logic is already tested within the core. Tests of the shell code "just" checks the integration of the functional core and the infrastructure.

## Shades of grey

Its within the own definition of placing some edge cases of functionality within the core although they are not side effect free. An example that may be quite easy to understand is logging. strictly speaking logging is a side effect (putting information on stdout). So, the ideal way would be to pass all the log messages back to the imperative shell. However, these side effects usually don't affect your application, may also hold a lot of information (timestamp, correlation id, ...) and probably you wouldn't write tests for proper logging. So there may be some rare cases, where it is up to the developer team to decice how strict you are. 

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

### Pass immutable objects in and out

#### Java

#### Java with Guava

Quite the same functionality is also provided by Apache Commons

#### Java with Vavr