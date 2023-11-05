package UNIT_3.learn.netty.firstExample;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

public class TestServer {
    public static void main(String[] args) throws Exception {
        // EventLoopGroup��Ϊʱ��ѭ���飬 �������Ϊһ����ѭ��������tomcat������������У�һ�㶼����һ����ѭ�������ڼ�������
        EventLoopGroup bossGroup = new NioEventLoopGroup(); // bossGroup ���ڽ��տͻ��˵����󣬵������д�������workGroup���д���
        EventLoopGroup workGroup = new NioEventLoopGroup(); // workGroup ���ڴ���ͻ��˵�����
        try {

            ServerBootstrap serverBootstrap = new ServerBootstrap();
            serverBootstrap.group(bossGroup, workGroup).channel(NioServerSocketChannel.class)
                    .childHandler(new TestServerInitializer()); // ��ʼ���ܵ������� - new TestServerInitializer() ��ʼ���Զ��崦����
            ChannelFuture channelFuture = serverBootstrap.bind(8999).sync(); // �󶨼����Ķ˿ں�
            channelFuture.channel().closeFuture().sync();  // �����ر�
        } finally {
            bossGroup.shutdownGracefully(); // bossGroup�Ż��ر�
            workGroup.shutdownGracefully(); // workGroup�Ż��ر�
        }
    }
}
