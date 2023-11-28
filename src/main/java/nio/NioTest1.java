package nio;

import java.nio.IntBuffer;
import java.security.SecureRandom;

public class NioTest1 {
    public static void main(String[] args) {
        IntBuffer buffer = IntBuffer.allocate(10);
        for (int i = 0; i < 5; i++) {
            int randomNo = new SecureRandom().nextInt(20);
            buffer.put(randomNo);
        }
        System.out.println("l1:" + buffer.limit()); // 10
        System.out.println("c1:" + buffer.limit()); // 10
        System.out.println("p0:" + buffer.position()); // 5
        buffer.flip(); // ·­×ª
        System.out.println("l2:" + buffer.limit()); // 5
        System.out.println("p1:" + buffer.position()); // 0
        System.out.println("c2:" + buffer.limit()); // 10
        while (buffer.hasRemaining()) {
            System.out.println("p2:" + buffer.position()); // 0
            System.out.println("l:" + buffer.limit()); // 5
            System.out.println("c:" + buffer.capacity()); // 10
            System.out.println(buffer.get());
        }
    }
}
