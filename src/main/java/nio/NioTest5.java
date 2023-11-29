package nio;


import java.nio.ByteBuffer;

public class NioTest5 {
    public static void main(String[] args) {
        // 存入类型一定要和取出类型保持一致
        ByteBuffer buffer = ByteBuffer.allocate(64);
        buffer.putInt(15);
        buffer.putLong(5000000L);
        buffer.putDouble(14.132131);
        buffer.putChar('a');
        buffer.putShort((short) 2);
        buffer.putChar('w');
        buffer.flip();
        System.out.println(buffer.getInt());
        System.out.println(buffer.getLong());
        System.out.println(buffer.getDouble());
        System.out.println(buffer.getChar());
        System.out.println(buffer.getShort());
        System.out.println(buffer.getChar());
    }
}
