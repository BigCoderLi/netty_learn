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
            SocketChannel socketChannel = serverSocketChannel.accept(); // ��������, �ȴ���������
            socketChannel.configureBlocking(true); // ����Ϊ����ģʽ, ��ʹ��selector, ���Ծ���Ҫ��������ģʽ(�˴�������ʵ����ɾ��, ��Ϊ�����accept����Ĭ�Ͼ�Ϊ����ģʽ)

            int readCount = 0;
            while (readCount != -1) {
               try {
                   readCount = socketChannel.read(byteBuffer);
               } catch (Exception e) {
                   e.printStackTrace();
               }
//               byteBuffer.clear(); // ÿ�ζ���, �轫position��Ϊ0, Ҳ���Ե�������ķ���
               byteBuffer.rewind();
            }
        }
    }
}
