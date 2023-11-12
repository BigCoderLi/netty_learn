handler和childHandler的区别：
1. handler用于处理服务器级别的事件, 如连接的建立; 主要服务于boss事件循环组的事件处理
2. childHandler用于处理具体通信事件, 如自定义的channelHandler; 主要服务于worker事件循环组的事件处理

加入IdleStateHandler心跳检测handler的原因: 虽然连接建立和连接断开可以通过回调的方式检测到, 但是有一些情况, 比如客户端开启了飞行模式、断开了wifi, 此时服务器是感知不到的。

