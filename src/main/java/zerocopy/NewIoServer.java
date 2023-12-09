package zerocopy;

import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;

public class NewIoServer {
    public static void main(String[] args) throws Exception{
        InetSocketAddress address = new InetSocketAddress(8899);
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        ServerSocket serverSocket = serverSocketChannel.socket();
        serverSocket.setReuseAddress(true);
        serverSocket.bind(address);

        ByteBuffer byteBuffer = ByteBuffer.allocate(314572800);
        while (true) {
            SocketChannel socketChannel = serverSocketChannel.accept(); // 阻塞方法, 等待接收连接
            socketChannel.configureBlocking(true); // 设置为阻塞模式, 因不使用selector, 所以就需要采用阻塞模式(此处代码其实可以删除, 因为上面的accept方法默认就为阻塞模式)

            int readCount = 0;
            while (readCount != -1) {
               try {
                   readCount = socketChannel.read(byteBuffer);
               } catch (Exception e) {
                   e.printStackTrace();
               }
//               byteBuffer.clear(); // 每次读完, 需将position置为0, 也可以调用下面的方法
               byteBuffer.rewind();
            }
        }
    }
}
