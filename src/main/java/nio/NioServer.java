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
    private static Map<String, SocketChannel> clientMap = new HashMap<>(); // ���ڴ��socketChannel, key:UUID, value:socketChannel

    public static void main(String[] args) throws Exception {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); // ��һ����������socketͨ��
        serverSocketChannel.configureBlocking(false); // ����Ϊ������
        ServerSocket serverSocket = serverSocketChannel.socket(); // ��ȡ���׽���, ��������ͨ��
        serverSocket.bind(new InetSocketAddress(8899)); // �׽��ְ󶨼����Ķ˿ں�

        Selector selector = Selector.open(); // ��һ��ѡ����, ���ڼ���ͨ���ϵ��¼�
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT); // ��serverSocketChannelע�ᵽѡ����, ��ָ����������ΪOP_ACCEPT(��ʾ���յ�һ���µ���������)

        while (true) { // ������ѭ�������ϼ����¼�������
            try {
                selector.select(); // ��������, ֱ���¼�����
                Set<SelectionKey> selectionKeys = selector.selectedKeys(); // �õ����е��¼�����
                selectionKeys.forEach(selectionKey -> { // �����¼�key
                    final SocketChannel client;
                    try {
                        if (selectionKey.isAcceptable()) { // ��ʾ���µ���������, �������Ӳ�ע����¼�(OP_READ)
                            System.out.println("Is Acceptable");
                            ServerSocketChannel server = (ServerSocketChannel) selectionKey.channel();
                            client = server.accept();
                            client.configureBlocking(false);
                            client.register(selector, SelectionKey.OP_READ);
                            String key = "[" + UUID.randomUUID() + "]";
                            clientMap.put(key, client);
                        } else if (selectionKey.isReadable()) { // ��ʾΪ����һ�����¼�, �����ݿɶ�����ȡ���ݣ�������ַ�����Ȼ����Ϣ���͸��������пͻ���
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
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) { // ��ȡ�����ߵ�senderKey
                                    if (client == entry.getValue()) {
                                        senderKey = entry.getKey();
                                        break;
                                    }
                                }
                                for (Map.Entry<String, SocketChannel> entry : clientMap.entrySet()) { // �����յ��Ŀɶ���Ϣת���������ͻ���
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
