package UNIT_5.lean.netty.thirdExample;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class MyChatClient {
    public static void main(String[] args) throws InterruptedException, IOException {
        EventLoopGroup eventLoopGroup = new NioEventLoopGroup();

        try {
            Bootstrap bootstrap = new Bootstrap();
            bootstrap.group(eventLoopGroup).channel(NioSocketChannel.class).handler(new MyChatClientInitializer());
            Channel channel = bootstrap.connect("localhost", 8899).sync().channel();

            // 接收键盘输入
            BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
            // 接收到键盘输入后, 向通道内写入数据
            for (;;) {
                channel.writeAndFlush(br.readLine() + "\r\n");
            }
        } finally {
            eventLoopGroup.shutdownGracefully();
        }
    }
}
