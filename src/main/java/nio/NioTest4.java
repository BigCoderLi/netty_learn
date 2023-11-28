package nio;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

public class NioTest4 {
    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("input.txt");
        FileOutputStream outputStream = new FileOutputStream("output.txt");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate(512);
        /*
         * 以下代码进行文件的读写的过程：
         * 1. byteBuffer.clear():position置为0, limit置为capacity(本例为512字节末尾)
         * 2. inputChannel.read(byteBuffer)：将channel中的字节读取到buff中, 同时buff的position置为42(本例中input.txt文件的字节数为42字节); read返回42, 继续执行
         * 3. byteBuffer.flip()：limit置为position, position置为0
         * 4. outputChannel.write(byteBuffer):将buff中的42字节写入到channel中(写入到out.txt中), 此时的position和limit重合(共同指向42字节处)
         * 5. 继续执行byteBuffer.clear():position置为0, limit置为capacity(本例为512字节末尾)
         * 6. inputChannel.read(byteBuffer)：将channel中的字节读取到buff中, 读取到的字节数为0, break
         */
        while (true) {
            /*
             * 如果不加byteBuffer.clear()的流程
             * 1. byteBuffer.clear()：position置为0, limit置为capacity(本例为512字节末尾)
             * 2. inputChannel.read(byteBuffer)：将channel中的字节读取到buff中, 同时buff的position置为42(本例中input.txt文件的字节数为42字节); read返回42, 继续执行
             * 3. byteBuffer.flip()：limit置为position, position置为0
             * 4. outputChannel.write(byteBuffer):将buff中的42字节写入到channel中(写入到out.txt中), 此时的position和limit重合(共同指向42字节处)
             * 5. inputChannel.read(byteBuffer)：因position和limit重合, 所以并为read到任何字节, 返回read的值为0
             * 6. byteBuffer.flip()：limit置为position, position置为0
             * 7. outputChannel.write(byteBuffer)：此时因position为0, limit为position(42字节处)且buff中的42字节并未清除, 所以程序会陷入死循环并一直向output.txt中写入input.txt中的42字节
             */
            byteBuffer.clear();

            int read = inputChannel.read(byteBuffer);
            System.out.println(read);
            if (-1 == read) {
                break;
            }
            byteBuffer.flip();
            outputChannel.write(byteBuffer);
        }
        inputChannel.close();
        outputChannel.close();
    }
}
