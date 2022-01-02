// package com.forkfork.cake.controller.socket;
//
// import java.util.List;
//
// import org.springframework.stereotype.Controller;
// import org.springframework.ui.Model;
// import org.springframework.web.bind.annotation.GetMapping;
// import org.springframework.web.bind.annotation.PathVariable;
// import org.springframework.web.bind.annotation.PostMapping;
// import org.springframework.web.bind.annotation.RequestMapping;
// import org.springframework.web.bind.annotation.RequestParam;
// import org.springframework.web.bind.annotation.ResponseBody;
// import org.springframework.web.servlet.ModelAndView;
// import org.springframework.web.servlet.mvc.support.RedirectAttributes;
//
// import com.forkfork.cake.domain.ChatRoom;
// import com.forkfork.cake.dto.socket.ChatRoomDTO;
// import com.forkfork.cake.repository.ChatRoomRepository;
//
// import lombok.RequiredArgsConstructor;
// import lombok.extern.log4j.Log4j2;
//
// @Controller
// @RequiredArgsConstructor
// @RequestMapping(value = "/chat")
// @Log4j2
// public class RoomController {
//
// 	private final ChatRoomRepository chatRoomRepository;
//
// 	@GetMapping("/room")
// 	public String rooms(Model model) {
// 		return "/chat/room";
// 	}
//
// 	@GetMapping("/rooms")
// 	@ResponseBody
// 	public List<ChatRoomDTO> room() {
// 		return chatRoomRepository.findAllRooms();
// 	}
//
// 	@PostMapping("/room")
// 	@ResponseBody
// 	public ChatRoomDTO createRoom(@RequestParam String name) {
// 		return chatRoomRepository.createChatRoomDTO(name);
// 	}
//
// 	@GetMapping("/room/enter/{roomId}")
// 	public String roomDetail(Model model, @PathVariable String roomId) {
// 		model.addAttribute("roomId", roomId);
// 		return "/chat/roomdetail";
// 	}
//
// 	@GetMapping("/room/{roomId}")
// 	@ResponseBody
// 	public ChatRoomDTO roomInfo(@PathVariable String roomId) {
// 		return chatRoomRepository.findRoomById(roomId);
// 	}
// }