ChannelGroup对象可用来存储channel对象, 调用其writeAndFlush方法可实现广播效果


UNIT_5 程序执行过程
1. chatServer启动
2. chatClient启动
3. MyChatServerHandler handlerAdded方法执行 -> channel加入到channelGroup
4. MyChatServerHandler channelActive方法执行
5. 客户端键盘输入, 发送消息到channel
6. MyChatServer channelRead0方法执行 -> 遍历channelGroup, 向channel中写入消息 
7. MyChatClient channelRead0方法执行 -> 控制台输出msg
8. 某客户端断开连接 
9. channelInactive方法执行
10. handlerRemoved方法执行, channelGroup中对应的channel移除(netty自动触发)
