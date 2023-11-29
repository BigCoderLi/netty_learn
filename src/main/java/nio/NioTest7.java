package nio;

import java.nio.ByteBuffer;
import java.nio.IntBuffer;

/**
 * 可以将一个读写buffer转换为一个读写buffer, 但不能将一个只读buffer转换为一个读写buffer
 */
public class NioTest7 {
    public static void main(String[] args) {
        ByteBuffer buffer = ByteBuffer.allocate(10);
        System.out.println(buffer.getClass()); // HeapByteBuffer
        for (int i = 0; i < buffer.capacity(); i++) {
            buffer.put((byte) i);
        }
        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        IntBuffer buffer1 = readOnlyBuffer.asIntBuffer();
        System.out.println("buff1:" + buffer1.getClass());  // ByteBufferAsIntBufferRB
        buffer1.put(1); // 抛出ReadOnlyBufferException
        System.out.println(readOnlyBuffer.getClass());
        readOnlyBuffer.position(0);
        readOnlyBuffer.put((byte) 2); // 抛出ReadOnlyBufferException
    }
}
