package com.forkfork.cake.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forkfork.cake.domain.ChatRoom;

public interface ChatRoomRepository extends JpaRepository<ChatRoom, Long> {
}
