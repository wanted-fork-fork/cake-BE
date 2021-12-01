package com.forkfork.cake.socket;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.CloseStatus;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.forkfork.cake.dto.socket.TestDto;
import com.forkfork.cake.util.JwtTokenUtil;
import com.google.gson.JsonObject;

import lombok.RequiredArgsConstructor;

@Component
@RequiredArgsConstructor
public class WebSocketHandler extends TextWebSocketHandler {

	private List<WebSocketSession> sessionList = new ArrayList<>();
	private final ObjectMapper objectMapper;
	private final JwtTokenUtil jwtTokenUtil;

	@Override
	public void afterConnectionEstablished(WebSocketSession session) throws Exception {

		sessionList.add(session);

		for (WebSocketSession s : sessionList) {
			s.sendMessage(new TextMessage(session.getId() + "님이 입장하셨습니다."));
		}
	}

	@Override
	protected void handleTextMessage(WebSocketSession session, TextMessage message) throws Exception {
		TestDto testDto = objectMapper.readValue(message.getPayload(), TestDto.class);
		String email = jwtTokenUtil.getSubject(testDto.getToken());
		String[] split = Objects.requireNonNull(session.getUri()).getQuery().split("room=");
		System.out.println(split[1]);
		for (WebSocketSession s : sessionList) {
			s.sendMessage(new TextMessage(email + ":" + split[1] + ":" + testDto.getMsg()));
		}
	}

	@Override
	public void afterConnectionClosed(WebSocketSession session, CloseStatus status) throws Exception {

		sessionList.remove(session);

		for (WebSocketSession s : sessionList) {
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
