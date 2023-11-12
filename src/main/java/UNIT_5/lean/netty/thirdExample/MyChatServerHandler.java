package UNIT_5.lean.netty.thirdExample;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.group.ChannelGroup;
import io.netty.channel.group.DefaultChannelGroup;
import io.netty.util.concurrent.GlobalEventExecutor;

public class MyChatServerHandler extends SimpleChannelInboundHandler<String> {
    // ���ڱ������е������ӵ�channel����
    private static ChannelGroup channelGroup = new DefaultChannelGroup(GlobalEventExecutor.INSTANCE);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, String msg) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.forEach(ch -> {
            if (channel != ch) {
                ch.writeAndFlush(channel.remoteAddress() + " ������Ϣ: " + msg + "\n");
            } else {
                ch.writeAndFlush("[�Լ�] " + msg + "\n");
            }
        });
    }

    /**
     * ��ʾ�ͻ��������˼��tcp�����ѽ���
     */
    @Override
    public void handlerAdded(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        // ��apiΪnetty�ṩ�ļ�㷽ʽ, �����channelGroup�е�����channel��writeAndFlush����
        channelGroup.writeAndFlush("[������] - " + channel.remoteAddress() + " ����\n");
        channelGroup.add(channel);
    }

    @Override
    public void handlerRemoved(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        channelGroup.writeAndFlush("[������] - " + channel.remoteAddress() + " �뿪\n");
//        channelGroup.remove(channel);  �˴�������ô�api, netty���Զ�����
    }

    /**
     * ��ʾchannel���ڻ״̬
     */
    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " ����");
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel channel = ctx.channel();
        System.out.println(channel.remoteAddress() + " ����");
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
