WebSocket����:
1. TextWebSocketFrame�� WebSocketЭ��淶�е���Ϣʵ��, Frame����˼Ϊ֡
2. WebSocketServerProtocolHandler netty�ṩ�����ڴ���WebSocketЭ��Ĵ�����, ����path����, ������servlet�е�path
3. ������WebSocketЭ�������ַ��ws://localhost:8899/ws
4. �������ͨ��javaScript���ʵ��WebSocketЭ��, �磺onOpen - ���ӽ����� onClose - ���ӶϿ�(�����ҳ��ˢ�»ᴥ��)��onMessage - �յ�����˵���Ϣ��Ӧ
5. �������F12�����߹���, �ɲ鿴wsЭ��Ľ�������Ϣ�շ��� �������ӵ�����Ϊws, ConnectionΪUpgrade(����); �����Ϣ�ɲ鿴�������Ϣ�շ�