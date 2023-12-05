Nio 
Buffer: 
    1. capacity: 当前buffer容器中的元素数量, 此变量一但allocate()后, 就不会再被改变
    2. position： buffer中将要被读写的下一个元素的索引，它不会超过limit
    3. limit： buffer中要被读写的最后一个元素的下一个元素的索引, 它不会超过capacity

读和写的概念区别：
1. 将数据放入buffer中称为读
2. 将数据从buffer中拿到外面成为写


ServerSocketChannel和SocketChannel的区别:
1. ServerSocketChannel:
    ServerSocketChannel 是用于监听新进来的 TCP 连接的通道。
    在服务器端，通过 ServerSocketChannel 可以监听一个指定的端口，接受客户端的连接请求。
    通过 ServerSocketChannel 的 accept() 方法可以获取一个表示客户端连接的 SocketChannel。
    通常，服务器端会创建一个 ServerSocketChannel 对象，然后将其绑定到一个特定的端口，以等待客户端的连接请求。
2. SocketChannel:
    SocketChannel 是用于建立客户端到服务器端的 TCP 连接的通道。
    在客户端，通过 SocketChannel 可以连接到指定的服务器端，并进行数据的读写。
    通过 SocketChannel 提供的方法可以读取和写入数据，也可以设置通道的阻塞模式等。
    客户端通常通过 SocketChannel.open() 来创建一个新的 SocketChannel。


Selector概念:
Selector 是 Java NIO 中的一个关键组件，用于实现非阻塞 I/O 操作。它的主要作用是监控多个通道的事件，以便在这些通道上进行读、写等操作，而无需为每个通道都创建一个独立的线程。这使得一个单独的线程能够有效地管理多个通道，提高系统的并发性能。

Selector 的主要作用：
1.  事件监听： Selector 可以监听多个通道上的事件，如连接就绪、数据可读、数据可写等。
2.  非阻塞 I/O： 通过 Selector，可以实现非阻塞的 I/O 操作。当一个通道注册到 Selector 上时，可以在单独的线程中等待事件的发生，而不需要通过阻塞的方式一直等待，提高了程序的效率。
3.  单线程管理多通道： 使用 Selector 可以通过一个单独的线程管理多个通道，减少线程的创建和切换开销，提高系统的资源利用率。