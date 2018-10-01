![Logo](https://github.com/anicolaspp/nSource/blob/master/lazy%20nSource%20..png)


[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.github.anicolaspp/nSource/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.github.anicolaspp/nSource)
[![Build Status](https://travis-ci.org/anicolaspp/nSource.svg?branch=master)](https://travis-ci.org/anicolaspp/nSource)

# nSource

**nSource** is a small, stream-like, fully featured and functional library to chain computation stages on top of regular Java Collections. This library is intended as a case of study for lazy design and to demonstrate how laziness is an important part when building performant software.

```xml
<dependency>
  <groupId>com.github.anicolaspp</groupId>
  <artifactId>nSource</artifactId>
  <version>1.0.1</version>
</dependency>
```

## Features

**nSource** has a few interesting characteristics.

- it is lazy, until the last materialization stage.
- its API is functional, so we can use declarative flow control.
- it has a familiar API, by using stream-like naming.
- It is optimized, so it operates on the minimum and only necessary elements of the underlying data source.

## Usage

**nSource** allows us to do the following. 

```java
RunnableStage<List<String>> s = nsource
                              .from(getNumbers())
                              .filter(x-> x % 2 == 0)
                              .map(x-> x.toString())
                              .toList();
List<String> f = s.run();
```

Notice that nothing happens until we call `.run()` of the corresponding `RunnableStage<>`. 

As we can see, this library can be seen in a similar way to what `Java Streams` offer and even though the intention is not to replace Streams, the library shows how the inception of lazy desing is a building blog when building smart components that need to present good performance. 

## Stream-like API

The main component of the **nSource** is `ComposableStage<>` and the followings are some of the present combinators. 

```java
    public <B> ComposableStage<B> map(Function<A, B> fn)
    public ComposableStage<A> filter(Predicate<A> predicate)
    public ComposableStage<A> take(int n)
    public ComposableStage<A> takeWhile(Predicate<A> predicate)
    public RunnableStage<List<A>> toList()
    public <B> RunnableStage<B> foldLeft(B zero, BiFunction<A, B, B> biFunction)
    public RunnableStage<Done> forEach(Consumer<A> consumer)
    public RunnableStage<Optional<A>> first()
    public RunnableStage<A> firstOrDefault(Supplier<A> defaultValue)
```

***This is NOT the entire list***

As we can appreciate, it shares a lot with `Java Streams` but it holds on laziness as much as it can. 

**nSource** `ComposableStage<>` are meant to be used for chaining operations. However, once we get a `RunnableStage<>` we should not reuse the corresponding `ComposableStage<>`.  

```java
String name = ....

RunnableStage<Integer> sum = nSource
                              .from(getValues(...))
                              .filter(v -> v.name.equals(name))
                              .map(v -> v.getAge())
                              .foldLeft(0, (a, b) -> a + b);

assert sum.run() == sum.run()
```

In here, the computations happens only once (the first time we call `.run()` and the second time the value is reused internally. 

On the other hand, the following might cause a source reuse, and that is not allowed. 

```java
ComposableStage<List<Integer>> stage = nSource
                              .from(getValues(...))
                              .filter(v -> v.name.equals(name))
                              .map(v -> v.getAge());

RunnableStage<Integer> sum = stage.foldLeft(0, (a, b) -> a + b);

RunnableStage<Done> printThem = stage.forEach(System.out::println);
```

This is perfectly valid since nothing has been executed just yet, but then we should only be able to materialize exactly one of them (the first one to call `.run()`. 

```java
printThem.run();
...
int sumValue = sum.run(); // MaterializationException thrown here. 
```

The first one to be materialized wins.

> This library is not intended to be used in production, but to present implementation details on lazy and smart design/implementation. 
