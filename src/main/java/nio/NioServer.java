package nio;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class NioServer {
    private static Map<String, SocketChannel> clientMap = new HashMap<>(); // 用于存放socketChannel, key:UUID, value:socketChannel

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); // 打开一个服务器端socket通道
        serverSocketChannel.configureBlocking(false); // 设置为非阻塞
        ServerSocket serverSocket = serverSocketChannel.socket(); // 获取到套接字, 用于网络通信
        serverSocket.bind(new InetSocketAddress(8899)); // 套接字绑定监听的端口号

        Selector selector = Selector.open(); // 打开一个选择器, 用于监听通道上的事件
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // 将serverSocketChannel注册到选择器, 并指定操作类型为OP_ACCEPT(表示接收到一个新的连接请求)

        while (true) { // 进入主循环，不断监听事件并处理
            try {
                selector.select(); // 阻塞方法, 直至事件发生
                Set<SelectionKey> selectionKeys = selector.selectedKeys(); // 拿到所有的事件集合
                selectionKeys.forEach(selectionKey -> { // 遍历事件key
                    final SocketChannel client;
                    try {
                        if (selectionKey.isAcceptable()) { // 表示有新的连接请求, 接受连接并注册读事件(OP_READ)
                            System.out.println("Is Acceptable");
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                            String key = "[" + UUID.randomUUID() + "]";
                            clientMap.put(key, client);
                        } else if (selectionKey.isReadable()) { // 表示为发生一个读事件, 有数据可读。读取数据，解码成字符串，然后将消息发送给其他所有客户端
                            System.out.println("Is Readable");
                            client = (SocketChannel) selectionKey.channel();
                            ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                            int count = client.read(readBuffer);
                            if (count > 0) {
                                readBuffer.flip();
                                Charset charset = StandardCharsets.UTF_8;
                                String receivedMessage = String.valueOf(charset.decode(readBuffer).array());
                                System.out.println(client + ":" + receivedMessage);
                                String senderKey = null;
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) { // 获取发送者的senderKey
                                    if (client == entry.getValue()) {
                                        senderKey = entry.getKey();
                                        break;
                                    }
                                }
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) { // 将接收到的可读消息转发给其它客户端
                                    if (!Objects.equals(entry.getKey(), senderKey)) {
                                        SocketChannel value = entry.getValue();
                                        ByteBuffer writeBuffer = ByteBuffer.allocate(1024);
                                        writeBuffer.put((senderKey + ":" + receivedMessage).getBytes());
                                        writeBuffer.flip();
                                        value.write(writeBuffer);
                                    }
                                }
                            }
                        }
                    } catch (Exception ex) {
                        ex.printStackTrace();
                    }
                });
                selectionKeys.clear();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }
}
