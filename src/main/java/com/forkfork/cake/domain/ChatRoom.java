package com.forkfork.cake.domain;

import java.util.LinkedList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class ChatRoom {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	private String roomId;

	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
	private List<ChatRoomMember> chatRoomMembers = new LinkedList<>();

	public void addChatRoomMember(ChatRoomMember chatRoomMember) {
		if (chatRoomMembers == null) {
			chatRoomMembers = new LinkedList<>();
		}
		chatRoomMembers.add(chatRoomMember);
		chatRoomMember.setChatRoom(this);
	}

	@OneToMany(mappedBy = "chatRoom", cascade = CascadeType.ALL)
	private List<ChatMsg> chatMsgs = new LinkedList<>();

	public void addChatMsg(ChatMsg chatMsg) {
		if (chatMsgs == null) {
			chatMsgs = new LinkedList<>();
		}
		chatMsgs.add(chatMsg);
		chatMsg.setChatRoom(this);
	}
}
