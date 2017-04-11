[Flatmap your RPC!](slides-flatmap-your-rpc.pdf)
=================

This talk demonstrates the best practices of building JVM servers with maximum CPU utilization and highly loaded IO.

The main question that arises is how to restructure your imperative code to make sure different parts of it are executed in different execution contexts and at the same time not to make it overcomplicated.

The proposed solution is to make use of the fundamental composability of functional paradigms.

### Demo project

This talk includes a demo project that demonstrates the benefits of writing non-blocking code to achieve higher throughput.

Two packages contain the same toy project implementation

- [vlad.fp.services.synchronous](demo/src/main/java/vlad/fp/services/synchronous) - with blocking RPC simulation
- [vlad.fp.services.asynchronous](demo/src/main/java/vlad/fp/services/asynchronous) - with non-blocking RPC simulation

After running each project individually and comparing their output it's easy to see the benefits of the asynchronous approach.

##### `SyncTest.java`
```
Throughput: 172 req/sec
Throughput: 176 req/sec
Throughput: 179 req/sec
Throughput: 180 req/sec
Throughput: 181 req/sec
Throughput: 180 req/sec
Throughput: 181 req/sec
```

##### `AsyncTest.java`
```
Throughput: 8430 req/sec
Throughput: 5879 req/sec
Throughput: 6797 req/sec
Throughput: 6149 req/sec
Throughput: 6983 req/sec
Throughput: 7448 req/sec
Throughput: 5975 req/sec
```

### Code examples

There are two main classes that demonstrate the code difference between synchronous and asynchronous approaches:

- [`FrontendServer.java`](demo/src/main/java/vlad/fp/services/synchronous/server/FrontendServer.java)
- [`FrontendServerF.java`](demo/src/main/java/vlad/fp/services/asynchronous/server/FrontendServerF.java) _(formatted as close as possible to the `FrontendServer.java`)_

There is also a cool [`lib`](demo/src/main/java/vlad/fp/lib) package that contains functional paradigms implemented in Java (mostly ported from [scalaz](https://github.com/scalaz/scalaz)). Although they look horrible, they do their job and are as pure as they could be.
