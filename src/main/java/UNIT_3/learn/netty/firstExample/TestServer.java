package UNIT_3.learn.netty.firstExample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {
    public static void main(String[] args) throws Exception {
        // EventLoopGroup译为时间循环组， 可以理解为一个死循环，类似tomcat，服务器编程中，一般都会有一个死循环，用于监听请求
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // bossGroup 用于接收客户端的请求，但不进行处理，交由workGroup进行处理
        EventLoopGroup workGroup = new NioEventLoopGroup(); // workGroup 用于处理客户端的请求
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer()); // 初始化管道处理器 - new TestServerInitializer() 初始化自定义处理器
            ChannelFuture channelFuture = serverBootstrap.bind(8999).sync(); // 绑定监听的端口号
            channelFuture.channel().closeFuture().sync();  // 监听关闭
        } finally {
            bossGroup.shutdownGracefully(); // bossGroup优化关闭
            workGroup.shutdownGracefully(); // workGroup优化关闭
        }
    }
}
