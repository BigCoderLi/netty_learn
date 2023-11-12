package UNIT_5.lean.netty.thirdExample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {
    // 用于保存所有的已连接的channel对象
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + " 发送消息: " + msg + "\n");
            } else {
                ch.writeAndFlush("[自己] " + msg + "\n");
            }
        });
    }

    /**
     * 表示客户端与服务端间的tcp连接已建立
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // 此api为netty提供的简便方式, 会调用channelGroup中的所有channel的writeAndFlush方法
        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 加入\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[服务器] - " + channel.remoteAddress() + " 离开\n");
//        channelGroup.remove(channel);  此处无需调用此api, netty会自动调用
    }

    /**
     * 表示channel处于活动状态
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 上线");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " 下线");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
