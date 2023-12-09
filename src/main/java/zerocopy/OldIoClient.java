package zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * ���� ��ͳIO, ������0������ʽ��IO��������
 */
public class OldIoClient {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost", 8899);

        String fileName = "G:\\��Java���ļ��� ��I������֪ʶ��ԭ���10�棩��.pdf";
        InputStream inputStream = new FileInputStream(fileName);
        DataOutputStream dataOutputStream =
                new DataOutputStream(socket.getOutputStream());
        byte[] buffer = new byte[4096];
        long readCount;
        long total = 0;

        long stratTime = System.currentTimeMillis();
        while ((readCount = inputStream.read(buffer)) >= 0) {
            total += readCount;
            dataOutputStream.write(buffer);
        }
        System.out.println("�������ֽ���: " + total + ", ��ʱ" + (System.currentTimeMillis() - stratTime));
        dataOutputStream.close();
        inputStream.close();
        socket.close();
    }
}
