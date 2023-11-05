package UNIT_3.learn.netty.firstExample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;

public class TestServerInitializer extends ChannelInitializer<SocketChannel> {
    /**
     * 连接一旦被建立，netty会创建 TestServerInitializer， 随后 initChannel 方法会被执行
     * <br/>
     * 此类用于定义netty提供的handler或自定义的handler
     * <br/>
     * 注： handler应定义为多例，不要定义非单例
     * @param ch            the {@link Channel} which was registered.
     */
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ChannelPipeline pipeline = ch.pipeline(); // pipeline为连接管道， 而channel则为管道中的一个个拦截器
        pipeline.addLast("httpServerCodec", new HttpServerCodec()); // httpSever编解码器
        pipeline.addLast("testHttpServerHandler", new TestHttpSeverHandler()); // 自定义的处理器
    }
}
