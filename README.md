# nSource

**nSource** is a small, stream-like, fully featured and functional library to chain computation stages on top of regular Java Collections. This library is insteded as a case of study for lazy design and to demonstrate how laziness is a important part when building perfomant software. 

## Features

**nSource** has a few interesting characteristics.

- it is lazy, until the last materialization stage.
- its API is functional, so we can use declarative flow control.
- it has a familiar API, by using stream-like naming.
- It is optimized, so it operates on the minimum and only necessary elements of the underlying data source.

## Usage

**nSource** allows us to do the following. 

```java
RunnableStage<Lis<String>> s = nsource
                              .from(getNumberes())
                              .filter(x-> x % 2 == 0)
                              .map(x-> x.toSting())
                              .toList();
List<String> f = s.run();
```

Notice that nothing happens until we call `.run()` of the corresponding `RunnableStage<>`. 

As we can see, this library can be seen in a similar way to what `Java Streams` offer and even though the intention is not to replace Streams, the library shows how the inception of lazy desing is a building blog when building smart components that need to present good performance. 

## Java-like API

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

> This library is not intended to be used in production, but to present implementation details on lazy and smart design/implementation. 
