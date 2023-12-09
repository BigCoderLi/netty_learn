package zerocopy;

import java.io.DataOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;
import java.net.Socket;

/**
 * 测试 传统IO, 不采用0拷贝方式的IO拷贝性能
 */
public class OldIoClient {
    public static void main(String[] args) throws Exception{
        Socket socket = new Socket("localhost", 8899);

        String fileName = "G:\\《Java核心技术 卷I：基础知识（原书第10版）》.pdf";
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
        System.out.println("发送总字节数: " + total + ", 耗时" + (System.currentTimeMillis() - stratTime));
        dataOutputStream.close();
        inputStream.close();
        socket.close();
    }
}
