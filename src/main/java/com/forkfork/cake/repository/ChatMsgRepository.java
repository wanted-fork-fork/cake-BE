package com.forkfork.cake.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forkfork.cake.domain.ChatMsg;

public interface ChatMsgRepository extends JpaRepository<ChatMsg, Long> {
}
