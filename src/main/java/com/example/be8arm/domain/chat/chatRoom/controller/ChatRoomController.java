package com.example.be8arm.domain.chat.chatRoom.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.be8arm.domain.chat.chatMessage.service.ChatMessageService;
import com.example.be8arm.domain.chat.chatRoom.controller.request.ModifyRequestBody;
import com.example.be8arm.domain.chat.chatRoom.controller.request.WriteRequestBody;
import com.example.be8arm.domain.chat.chatRoom.dto.ChatRoomListDto;
import com.example.be8arm.domain.chat.chatRoom.dto.ChatRoomMemberInfoDto;
import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoom;
import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoomMember;
import com.example.be8arm.domain.chat.chatRoom.service.ChatRoomService;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomService chatRoomService;
	private final ChatMessageService chatMessageService;

	@GetMapping("/{roomId}")
	public ResponseEntity<?> showRoom(
		@PathVariable final long roomId,
		@AuthenticationPrincipal UserDetails user
	) {
		if (!chatRoomService.isIncludeMe(user.getId(), roomId)) {
			return ResponseEntity.badRequest().body("채팅방 입장 권한이 없습니다.");
		}

		ChatRoomMember chatRoomMember = chatRoomService.findChatRoomMemberByChatRoomIdAndMemberId(user.getId(), roomId);
		return ResponseEntity.ok(chatRoomMember);
	}

	@GetMapping("/{roomId}/messages")
	public ResponseEntity<?> showMessages(
		@PathVariable final long roomId,
		@AuthenticationPrincipal UserDetails user) {
		Optional<ChatRoom> chatRoomOptional = chatRoomService.findById(roomId);
		//TODO 페이지네이션으로 무한스크롤 구현
		if (chatRoomOptional.isPresent()) {
			ChatRoom chatRoom = chatRoomOptional.get();
			return ResponseEntity.ok(chatRoom.getChatMessages());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/list")
	public ResponseEntity<?> showList(
		@AuthenticationPrincipal UserDetails user) {
		List<ChatRoomListDto> chatRooms = chatRoomService.findByMemberId(user.getId());
		return ResponseEntity.ok(chatRooms);
	}

	@PostMapping("/{roomId}/write")
	public ResponseEntity<?> write(
		@PathVariable final long roomId,
		@AuthenticationPrincipal UserDetails user,
		@RequestBody final WriteRequestBody requestBody
	) {
		chatMessageService.writeAndSend(roomId, user.getName(), requestBody.getContent(), "created", user.getId());

		return ResponseEntity.ok("성공");
	}

	@GetMapping("/make/{theirInfo}")
	public ResponseEntity<?> makeChatRoom(
		@AuthenticationPrincipal UserDetails user,
		@PathVariable String theirEmail //
	) {
		ChatRoomMemberInfoDto myInfoDto = new ChatRoomMemberInfoDto(user.getId(), user.getName(), user.getImgUrl());
		ChatRoomMemberInfoDto theirInfoDto = chatRoomService.createInfoDtoByEmail(theirEmail);

		Long chatRoomId;
		//이미 존재 하는 방일 때 0보다 큰값이 return됨
		if ((chatRoomId = chatRoomService.findChatRoom(user.getId(), theirInfoDto.getId())) <= 0) {
			chatRoomId = chatRoomService.makeChatRoom(myInfoDto, theirInfoDto);
			chatMessageService.writeAndSend(chatRoomId, user.getName(), "생성", "created", user.getId());
		} else {
			chatRoomId = chatRoomService.findChatRoom(user.getId(), theirInfoDto.getId());
		}

		ChatRoomMember chatRoomMember = chatRoomService.findChatRoomMemberByChatRoomIdAndMemberId(user.getId(),
			chatRoomId);

		return ResponseEntity.ok(chatRoomMember);

	}

	@GetMapping("/exit/{chatRoomId}")
	public ResponseEntity<?> exitChatRoom(
		@PathVariable Long chatRoomId,
		@AuthenticationPrincipal UserDetails user) {

		if (!chatRoomService.isIncludeMe(user.getId(), chatRoomId)) {
			return ResponseEntity.badRequest().body("권한이 없습니다.");
		}

		chatMessageService.writeAndSend(chatRoomId, user.getName(), "퇴장", "deleted", user.getId());

		if (!chatRoomService.deleteChatRoomByMemberIdAndChatRoomId(user.getId(), chatRoomId)) {
			return ResponseEntity.unprocessableEntity().body("채팅방을 나가는데 실패했습니다.");
		}

		return ResponseEntity.ok("성공");
	}

	@PatchMapping("/modify/{chatRoomId}")
	public ResponseEntity<?> modifyChatRoomName(
		@PathVariable Long chatRoomId,
		@AuthenticationPrincipal UserDetails user,
		@RequestBody final ModifyRequestBody modifyBody) {

		if (!chatRoomService.isIncludeMe(user.getId(), chatRoomId)) {
			return ResponseEntity.badRequest().body("권한이 없습니다.");
		}

		chatRoomService.modifyChatRoomName(user.getId(), chatRoomId, modifyBody.getChatRoomName());

		return ResponseEntity.ok("성공");
	}
}
