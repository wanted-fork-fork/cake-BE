package com.forkfork.cake.controller.socket;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessageType;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.web.bind.annotation.RestController;

import com.forkfork.cake.dto.socket.ChatMessageDTO;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
public class StompChatController {

	private final SimpMessagingTemplate template; // 특정 브로커로 메시지를 전달

	//Client가 SEND할 수 있는 경로
	//stompConfig에서 설정한 applicationDestinationPrefixes와 @MessageMapping 경로가 병합됨
	//"/pub/chat/enter"
	// @MessageMapping(value = "/views/chat/enter")
	// public void enter(ChatMessageDTO message){
	// 	message.setMessage(message.getWriter() + "님이 채팅방에 참여하였습니다.");
	// 	template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	// }
	//
	// @MessageMapping(value = "/views/chat/message")
	// public void message(ChatMessageDTO message){
	// 	template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	// }

	@MessageMapping("/chat/message")
	public void message(ChatMessageDTO message, SimpMessageHeaderAccessor simpMessageHeaderAccessor) {
		SimpMessageType messageType = simpMessageHeaderAccessor.getMessageType();
		System.out.println("messageType = " + messageType);
		if (ChatMessageDTO.MessageType.ENTER.equals(message.getType()))
			message.setMessage(message.getSender() + "님이 입장하셨습니다.");
		template.convertAndSend("/sub/chat/room/" + message.getRoomId(), message);
	}
}
