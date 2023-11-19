package UNIT_8.learn.netty.sixthExample;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandler;
import io.netty.channel.SimpleChannelInboundHandler;

public class TestServerHandler extends SimpleChannelInboundHandler<MyDataInfo.MyMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, MyDataInfo.MyMessage msg) throws Exception {
        MyDataInfo.MyMessage.DataType dataType = msg.getDataType();
        // 根据不同消息类型解析出对应的消息实体
        if (dataType == MyDataInfo.MyMessage.DataType.PersonType) {
            MyDataInfo.Person person = msg.getPerson();
            System.out.println(person.getName());
            System.out.println(person.getAge());
            System.out.println(person.getAddress());
        } else if (dataType == MyDataInfo.MyMessage.DataType.DogType) {
            MyDataInfo.Dog dog = msg.getDog();
            System.out.println(dog.getName());
            System.out.println(dog.getAge());
        } else {
            MyDataInfo.Cat cat = msg.getCat();
            System.out.println(cat.getName());
            System.out.println(cat.getCity());
        }
    }
}
