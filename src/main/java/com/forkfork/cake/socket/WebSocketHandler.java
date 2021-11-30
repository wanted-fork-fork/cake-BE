package com.forkfork.cake.socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

@Component
public class WebSocketHandler extends TextWebSocketHandler {

	private List<WebSocketSession> sessionList = new ArrayList<>();

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		sessionList.add(session);

		for(WebSocketSession s : sessionList) {
			s.sendMessage(new TextMessage(session.getId() + "님이 입장하셨습니다."));
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {

		for(WebSocketSession s : sessionList) {
			s.sendMessage(new TextMessage(session.getId() + ":" + message.getPayload()));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {


		sessionList.remove(session);

		for(WebSocketSession s : sessionList) {
			s.sendMessage(new TextMessage(session.getId() + "님이 퇴장하셨습니다."));
		}
	}
	// @Override
	// protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
	// 	String payload = message.getPayload();
	// 	System.out.println("payload = " + payload);
	// 	System.out.println("session = " + session.getId());
	// 	System.out.println("session = " + session.getUri());
	//
	// 	TextMessage initialGreeting = new TextMessage("Welcome to Swoomi Chat Server ~O_O~");
	// 	session.sendMessage(initialGreeting);
	// }
}
