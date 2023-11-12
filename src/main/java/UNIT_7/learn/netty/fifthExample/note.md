WebSocket程序:
1. TextWebSocketFrame： WebSocket协议规范中的消息实体, Frame的意思为帧
2. WebSocketServerProtocolHandler netty提供的用于处理WebSocket协议的处理器, 其中path参数, 类似于servlet中的path
3. 完整的WebSocket协议请求地址：ws://localhost:8899/ws
4. 浏览器可通过javaScript编程实现WebSocket协议, 如：onOpen - 连接建立、 onClose - 连接断开(浏览器页面刷新会触发)、onMessage - 收到服务端的消息响应
5. 浏览器的F12开发者工具, 可查看ws协议的建立及消息收发： 建立连接的请求为ws, Connection为Upgrade(升级); 点击消息可查看具体的消息收发