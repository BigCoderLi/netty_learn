netty服务启动流程
1. -> 建立server
2. -> 初始化eventLoopGroup
3. -> 注册initializer
4. -> 加载initializer中的handler处理器
5. -> 客户端连接建立
6. -> 执行回调方法 channelRead0

channelHandler执行流程
1. ChannelInboundHandlerAdapter.handlerAdded
2. ChannelInboundHandlerAdapter.channelRegistered
3. ChannelInboundHandlerAdapter.channelActive
4. channelRead0
5. ChannelInboundHandlerAdapter.channelInactive
6. ChannelInboundHandlerAdapter.channelUnregistered

windows查看端口情况命令：netstat -an | find "8999"        ESTABLISHED 表示已建立连接