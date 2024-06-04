
### BIO NIO AIO
- BIO：同步阻塞，一个连接一个线程，Client有连接请求时Server端就需要启动一个线程进行处理。线程开销大。
- NIO：同步非阻塞，Client的连接请求都会注册到多路复用器Selector上，Selector轮询到连接有I/O请求时才启动一个线程进行处理。
- AIO：异步非阻塞，基于事件和回调机制实现的，Client的请求之后会直接返回，不会堵塞在那里，当后台处理完成，操作系统会通知相应的线程进行后续的操作
BIO是面向流的，NIO是面向缓冲区的；BIO的各种流是阻塞的。而NIO是非阻塞的；BIO的Stream是单向的，而NIO的channel是双向的。

### Netty 工作流程
NioEventLoopGroup -> 包含一组NioEventLoop -> 每个eventLoop 都包含一个Selector -> 监听注册在其上的 SocketChannel 
    -> 放入taskQueue/delayedTaskQueue -> 执行任务（多个Channel共享一个EventLoop，它们的事件会被加入到同一个任务队列中）

每个 BossNioEventLoop 中循环执行以下三个步骤：
1. select：轮训注册在其上的 ServerSocketChannel 的 accept 事件（OP_ACCEPT 事件）
2. processSelectedKeys：处理 accept 事件，与客户端建立连接，生成一个 NioSocketChannel，并将其注册到某个 WorkerNioEventLoop 上的 Selector 上
3. runAllTasks：再去以此循环处理任务队列中的其他任务
每个 WorkerNioEventLoop 中循环执行以下三个步骤： 
4. select：轮训注册在其上的 NioSocketChannel 的 read/write 事件（OP_READ/OP_WRITE 事件）
5. processSelectedKeys：在对应的 NioSocketChannel 上处理 read/write 事件
6. runAllTasks：再去以此循环处理任务队列中的其他任务
在以上两个processSelectedKeys步骤中，会在 ChannelPipeline 里处理对应的 Channel，Pipeline 中维护了很多的ChannelHandler（拦截处理器、过滤处理器、自定义处理器等）

### 高性能的原因
1. 多路复用,非阻塞io
2. 主从 Reactor 多线程模型
3. 零拷贝  
4. 内存池化   将没有被引用的ByteBuf对象放入对象缓存池中，需要时重新从对象缓存池中取出，而不需要重新创建。
5. 堆外内存     使用 ByteBuf 来操作堆外内存，通过引用计数机制和内存池来管理堆外内存的分配和释放。相比于堆，释放和分配空间昂贵，读更快（因为不用复制到用户空间缓冲区）
6. 无锁化串行设计   消息的处理尽可能在一个线程内完成，期间不进行线程切换，避免了多线程竞争和同步锁的使用
7. 高性能编解码


### Netty 线程模型和 Reactor 模式
- **线程模型**： Netty 的线程模型采用了多线程的方式来处理并发请求。主要包括两类线程：Boss 线程和 Worker 线程。Boss 线程负责接收客户端的连接，并将连接注册到 Worker 线程的 Selector 上，而 Worker 线程则负责处理 I/O 事件和业务逻辑。这种线程模型能够有效地利用多核 CPU，并且在高并发情况下能够提供更好的性能表现。
- **Reactor模式**： Reactor 模式是一种处理并发 I/O 的设计模式，它基于事件驱动的思想。在 Reactor 模式中，有一个 Reactor（反应器）对象负责监听事件，并根据事件的类型分发给相应的处理器进行处理。在 Netty 中，Reactor 模式通过 Selector 实现，Selector 负责监听各种 I/O 事件，并将事件分发给对应的处理器进行处理，从而实现了高效的并发 I/O 处理能力。

### Netty 的零拷贝
1. 使用堆外内存（Direct Memory）：Netty 使用堆外内存来进行数据的存储和传输，这样可以避免数据在堆内存和堆外内存之间的复制，减少了数据拷贝的次数。
2. FileChannel.transferTo() 和 FileChannel.transferFrom()：数据可以直接从文件系统缓存（或内存映射文件）传输到目标Channel，而无需经过用户空间缓冲区，从而实现零拷贝。
3. CompositeByteBuf：Netty 提供了 CompositeByteBuf 类型，它可以将多个 ByteBuf 合并成一个逻辑上的 ByteBuf，避免了数据复制，提高了数据传输效率

### 缓存行
- 缓存行的大小是指处理器从主存中读取数据时一次性取回的数据块的大小。缓存行的大小是由硬件设计决定的，通常是2的幂次方，常见的大小包括 64 字节、128 字节等。
- 在大多数现代处理器架构中，常见的缓存行大小为 64 字节。这意味着当处理器从主存中读取数据时，会一次性取回 64 字节的数据，并将这些数据加载到处理器的缓存中。如果程序访问的数据位于同一缓存行中，那么后续的数据访问会更快，因为这些数据已经被加载到了缓存中。

### 伪共享
伪共享（False Sharing）是指多个线程同时修改互相独立的变量，但这些变量被存储在同一缓存行中，导致每次修改其中一个变量时，会导致整个缓存行的数据在不同的CPU核心之间来回传递，从而引起性能下降。
通常情况下，缓存在处理数据时以缓存行为单位进行读取和写入。如果多个变量被存储在同一缓存行中，当一个线程修改其中一个变量时，会导致整个缓存行无效，其他线程访问同一缓存行中的变量时就需要重新加载最新的数据，即使它们实际上在操作不同的变量。这种情况会导致不必要的缓存数据传输，降低了程序的性能。

解决伪共享问题的方法通常包括：
- 填充（Padding）：通过在变量之间增加填充字段，使得它们被存储在不同的缓存行中，从而避免不必要的缓存行失效。
- 对齐（Alignment）：确保变量被正确对齐，使得它们被存储在不同的缓存行中。
- 使用本地性原则：尽量让同一线程对同一变量的修改更频繁，减少不同线程对相邻变量的竞争。

### Mpsc Queue
 Multi Producer Single Consumer 多生产者单消费者，Eventloop里的TaskQueue就是用的这个
 ```
 public class FalseSharingPadding ｛ 
    protected Long p1, p2, p3, p4, p5, p6, p7;
    protected volatile Long value = OL;
    protected Long p9, p10, p11, p12, p13, p14, p15;
｝
 ```
- 解决伪共享问题：变量 value 前后都填充了7个 long 类型的变量，可以保证在多线程访问 value 变量时，value 与其他不相关的变量处于不同的 Cache Line。
- 入队使用CAS，队列长度使用volatile修饰，保证线程安全

### unsafe 和 四种类型的内存屏障，LoadLoad、 Store Store、 LoadStore  StoreLoad
