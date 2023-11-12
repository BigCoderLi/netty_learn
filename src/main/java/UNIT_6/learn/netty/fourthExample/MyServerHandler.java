package UNIT_6.learn.netty.fourthExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;

public class MyServerHandler extends ChannelInboundHandlerAdapter {
    /**
     * ��д�û��¼�������
     * ��׽��д�����¼�
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            String eventType = null;
            switch (event.state()) {
                case READER_IDLE:
                    eventType = "������";
                    break;
                case WRITER_IDLE:
                    eventType = "д����";
                    break;
                case ALL_IDLE:
                    eventType = "��д����";
                    break;
            }
            System.out.println(ctx.channel().remoteAddress() + " ��ʱ�¼��� " + eventType);
            ctx.channel().close();
        }
    }
}
