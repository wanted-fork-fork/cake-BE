package com.forkfork.cake.service;

import org.springframework.stereotype.Service;

import com.forkfork.cake.repository.ChatRoomMemberRepository;
import com.forkfork.cake.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatMsgService {

	private final ChatRoomRepository chatRoomRepository;
	private final ChatRoomMemberRepository chatRoomMemberRepository;

}
