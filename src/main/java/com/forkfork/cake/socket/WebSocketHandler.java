package com.forkfork.cake.socket;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		String payload = message.getPayload();
		System.out.println("payload = " + payload);

		TextMessage initialGreeting = new TextMessage("Welcome to Swoomi Chat Server ~O_O~");
		session.sendMessage(initialGreeting);
	}
}
