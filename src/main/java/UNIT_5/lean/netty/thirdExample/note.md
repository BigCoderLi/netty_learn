ChannelGroup����������洢channel����, ������writeAndFlush������ʵ�ֹ㲥Ч��


UNIT_5 ����ִ�й���
1. chatServer����
2. chatClient����
3. MyChatServerHandler handlerAdded����ִ�� -> channel���뵽channelGroup
4. MyChatServerHandler channelActive����ִ��
5. �ͻ��˼�������, ������Ϣ��channel
6. MyChatServer channelRead0����ִ�� -> ����channelGroup, ��channel��д����Ϣ 
7. MyChatClient channelRead0����ִ�� -> ����̨���msg
8. ĳ�ͻ��˶Ͽ����� 
9. channelInactive����ִ��
10. handlerRemoved����ִ��, channelGroup�ж�Ӧ��channel�Ƴ�(netty�Զ�����)
