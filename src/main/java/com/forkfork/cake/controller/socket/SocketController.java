package com.forkfork.cake.controller.socket;

import javax.servlet.http.HttpServletRequest;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.forkfork.cake.domain.ChatRoom;
import com.forkfork.cake.domain.Study;
import com.forkfork.cake.domain.User;
import com.forkfork.cake.dto.socket.request.CreateChatRoomRequest;
import com.forkfork.cake.service.ChatRoomService;
import com.forkfork.cake.service.StudyService;
import com.forkfork.cake.service.UserService;
import com.forkfork.cake.util.JwtTokenUtil;
import com.forkfork.cake.util.ResFormat;

import lombok.RequiredArgsConstructor;

@RestController
@RequiredArgsConstructor
@RequestMapping("/chat")
public class SocketController {

	private final ChatRoomService chatRoomService;
	private final UserService userService;
	private final StudyService studyService;
	private final JwtTokenUtil jwtTokenUtil;


}
