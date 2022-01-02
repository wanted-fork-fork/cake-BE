package com.forkfork.cake.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.forkfork.cake.domain.ChatRoomMember;

public interface ChatRoomMemberRepository extends JpaRepository<ChatRoomMember, Long> {
}
