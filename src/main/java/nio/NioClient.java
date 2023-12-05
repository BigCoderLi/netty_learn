package nio;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.time.LocalDateTime;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class NioClient {
    public static void main(String[] args) {
        try {
            SocketChannel socketChannel = SocketChannel.open();
            socketChannel.configureBlocking(false);
            Selector selector = Selector.open();
            socketChannel.register(selector, SelectionKey.OP_CONNECT); // 注册连接事件
            socketChannel.connect(new InetSocketAddress("127.0.0.1", 8899));
            while (true) {
                selector.select();
                Set<SelectionKey> keySet = selector.selectedKeys();
                for (SelectionKey selectionKey : keySet) {
                    if (selectionKey.isConnectable()) { // 可连接事件
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        if (client.isConnectionPending()) { // 正在进行连接操作
                            client.finishConnect(); // 确立连接
                            ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                            writeBuffer.put((LocalDateTime.now() + "success").getBytes());
                            writeBuffer.flip();
                            client.write(writeBuffer);

                            ExecutorService executorService = Executors.newSingleThreadExecutor(Executors.defaultThreadFactory());
                            executorService.submit(() -> { // 启动一个新的线程, 用于监听标准输入(键盘) 的输入
                               while (true) {
                                   try {
                                       writeBuffer.clear(); // 先清空writerBuffer
                                       InputStreamReader input = new InputStreamReader(System.in);
                                       BufferedReader br = new BufferedReader(input);
                                       String sendMessage = br.readLine(); // 读取键盘输入
                                       writeBuffer.put(sendMessage.getBytes());
                                       writeBuffer.flip();
                                       client.write(writeBuffer); // 向服务端写入消息
                                   } catch (Exception e) {
                                        e.printStackTrace();
                                   }
                               }
                            });
                        }
                        client.register(selector, SelectionKey.OP_READ); // 注册可读事件, 用于监听服务端发送的消息
                    } else if (selectionKey.isReadable()) { // 可读事件发生
                        System.out.println("isReadAble Happen.");
                        SocketChannel client = (SocketChannel) selectionKey.channel();
                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                        int count = client.read(readBuffer); // 从通道内读取来自服务端的消息
                        if (count > 0) {
                            String receiveMessage = new String(readBuffer.array(), 0, count);
                            System.out.println(receiveMessage);
                        }
                    }
                }
                keySet.clear();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
