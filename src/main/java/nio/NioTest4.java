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
         * ���´�������ļ��Ķ�д�Ĺ��̣�
         * 1. byteBuffer.clear():position��Ϊ0, limit��Ϊcapacity(����Ϊ512�ֽ�ĩβ)
         * 2. inputChannel.read(byteBuffer)����channel�е��ֽڶ�ȡ��buff��, ͬʱbuff��position��Ϊ42(������input.txt�ļ����ֽ���Ϊ42�ֽ�); read����42, ����ִ��
         * 3. byteBuffer.flip()��limit��Ϊposition, position��Ϊ0
         * 4. outputChannel.write(byteBuffer):��buff�е�42�ֽ�д�뵽channel��(д�뵽out.txt��), ��ʱ��position��limit�غ�(��ָͬ��42�ֽڴ�)
         * 5. ����ִ��byteBuffer.clear():position��Ϊ0, limit��Ϊcapacity(����Ϊ512�ֽ�ĩβ)
         * 6. inputChannel.read(byteBuffer)����channel�е��ֽڶ�ȡ��buff��, ��ȡ�����ֽ���Ϊ0, break
         */
        while (true) {
            /*
             * �������byteBuffer.clear()������
             * 1. byteBuffer.clear()��position��Ϊ0, limit��Ϊcapacity(����Ϊ512�ֽ�ĩβ)
             * 2. inputChannel.read(byteBuffer)����channel�е��ֽڶ�ȡ��buff��, ͬʱbuff��position��Ϊ42(������input.txt�ļ����ֽ���Ϊ42�ֽ�); read����42, ����ִ��
             * 3. byteBuffer.flip()��limit��Ϊposition, position��Ϊ0
             * 4. outputChannel.write(byteBuffer):��buff�е�42�ֽ�д�뵽channel��(д�뵽out.txt��), ��ʱ��position��limit�غ�(��ָͬ��42�ֽڴ�)
             * 5. inputChannel.read(byteBuffer)����position��limit�غ�, ���Բ�Ϊread���κ��ֽ�, ����read��ֵΪ0
             * 6. byteBuffer.flip()��limit��Ϊposition, position��Ϊ0
             * 7. outputChannel.write(byteBuffer)����ʱ��positionΪ0, limitΪposition(42�ֽڴ�)��buff�е�42�ֽڲ�δ���, ���Գ����������ѭ����һֱ��output.txt��д��input.txt�е�42�ֽ�
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
