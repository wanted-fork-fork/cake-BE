package com.forkfork.cake.service;

import org.springframework.stereotype.Service;

import com.forkfork.cake.domain.ChatRoom;
import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.User;
import com.forkfork.cake.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ChatRoomService {

	private final ChatRoomRepository chatRoomRepository;


}
