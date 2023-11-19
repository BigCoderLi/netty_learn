package UNIT_8.learn.netty.sixthExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import java.util.Random;

public class TestClientHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {

    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        int randomInt = new Random().nextInt(3);
        MyDataInfo.MyMessage myMessage = null;
        // ���������, �����������Ϣ����
        if (0 == randomInt) {
            myMessage =
                    MyDataInfo.MyMessage.newBuilder()
                            .setDataType(MyDataInfo.MyMessage.DataType.PersonType)
                            .setPerson(MyDataInfo.Person.newBuilder().setAge(20).setName("����").setAddress("����").build()).build();

        } else if (1 == randomInt) {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.DogType)
                    .setDog(MyDataInfo.Dog.newBuilder().setAge(2).setName("Tom").build()).build();
        } else {
            myMessage = MyDataInfo.MyMessage.newBuilder()
                    .setDataType(MyDataInfo.MyMessage.DataType.CatType)
                    .setCat(MyDataInfo.Cat.newBuilder().setName("Jerry").setCity("�ൺ").build()).build();
        }

        ctx.channel().writeAndFlush(myMessage);
    }
}
