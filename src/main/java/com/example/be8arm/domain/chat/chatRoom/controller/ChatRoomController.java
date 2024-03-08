package com.example.be8arm.domain.chat.chatRoom.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.be8arm.domain.chat.chatMessage.service.ChatMessageService;
import com.example.be8arm.domain.chat.chatRoom.controller.request.ModifyRequestBody;
import com.example.be8arm.domain.chat.chatRoom.controller.request.WriteRequestBody;
import com.example.be8arm.domain.chat.chatRoom.dto.ChatRoomInfoDto;
import com.example.be8arm.domain.chat.chatRoom.dto.ChatRoomListDto;
import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoom;
import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoomMember;
import com.example.be8arm.domain.chat.chatRoom.service.ChatRoomService;
import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.member.service.MemberService;
import com.example.be8arm.global.security.UserPrincipal;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/chat/room")
@RequiredArgsConstructor
public class ChatRoomController {
	private final ChatRoomService chatRoomService;
	private final ChatMessageService chatMessageService;
	private final MemberService memberService;

	@GetMapping("/{roomId}")
	public ResponseEntity<?> showRoom(
		@PathVariable final long roomId,
		@AuthenticationPrincipal UserPrincipal user
	) {
		if (!chatRoomService.isIncludeMe(user.getMember().getId(), roomId)) {
			return ResponseEntity.badRequest().body("채팅방 입장 권한이 없습니다.");
		}

		ChatRoomMember chatRoomMember = chatRoomService
			.findChatRoomMemberByChatRoomIdAndMemberId(user.getMember().getId(), roomId);

		ChatRoomInfoDto chatRoomInfoDto = new ChatRoomInfoDto(chatRoomMember);

		return ResponseEntity.ok(chatRoomInfoDto);
	}

	@GetMapping("/{roomId}/messages")
	public ResponseEntity<?> showMessages(
		@PathVariable final long roomId,
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "30") int size
	) {
		Optional<ChatRoom> chatRoomOptional = chatRoomService.findById(roomId);
		//TODO 페이지네이션으로 무한스크롤 구현
		if (chatRoomOptional.isPresent()) {
			ChatRoom chatRoom = chatRoomOptional.get();
			Pageable pageable = PageRequest.of(page, size);

			chatMessageService.showChatMessagesWithPage(roomId, pageable);
			return ResponseEntity.ok(chatRoom.getChatMessages());
		} else {
			return ResponseEntity.notFound().build();
		}
	}

	@GetMapping("/list")
	public ResponseEntity<?> showList(
		@AuthenticationPrincipal UserPrincipal user) {
		//TODO 무한스크롤 구현
		List<ChatRoomListDto> chatRooms = chatRoomService.findByMemberId(user.getMember().getId());
		return ResponseEntity.ok(chatRooms);
	}

	@PostMapping("/{roomId}/write")
	public ResponseEntity<?> write(
		//TODO roomId를 request에 넣을지 말지
		@PathVariable final long roomId,
		@AuthenticationPrincipal UserPrincipal user,
		@RequestBody final WriteRequestBody requestBody
	) {
		chatMessageService.writeAndSend(roomId, user.getMember().getName(),
			requestBody.getContent(), "created", user.getMember().getId());

		return ResponseEntity.ok("성공");
	}

	@GetMapping("/make/{theirInfo}")
	public ResponseEntity<?> makeChatRoom(
		@AuthenticationPrincipal UserPrincipal user,
		@PathVariable String theirUsername //
	) {
		Member myMember = user.getMember();
		Member theirMember = memberService.findByUsername(theirUsername);

		Long chatRoomId;
		//이미 존재 하는 방일 때 0보다 큰값이 return됨
		if ((chatRoomId = chatRoomService.findChatRoom(myMember.getId(), theirMember.getId())) <= 0) {
			chatRoomId = chatRoomService.makeChatRoom(user.getMember(), theirMember);
			chatMessageService.writeAndSend(chatRoomId, myMember.getName(), "생성", "created", myMember.getId());
		} else {
			chatRoomId = chatRoomService.findChatRoom(myMember.getId(), theirMember.getId());
		}

		ChatRoomMember chatRoomMember = chatRoomService.findChatRoomMemberByChatRoomIdAndMemberId(myMember.getId(),
			chatRoomId);
		ChatRoomInfoDto chatRoomInfoDto = new ChatRoomInfoDto(chatRoomMember);

		return ResponseEntity.ok(chatRoomInfoDto);

	}

	@DeleteMapping("/exit/{chatRoomId}")
	public ResponseEntity<?> exitChatRoom(
		@PathVariable Long chatRoomId,
		@AuthenticationPrincipal UserPrincipal user) {

		if (!chatRoomService.isIncludeMe(user.getMember().getId(), chatRoomId)) {
			return ResponseEntity.badRequest().body("권한이 없습니다.");
		}

		chatMessageService.writeAndSend(chatRoomId, user.getMember().getName(), "퇴장", "deleted",
			user.getMember().getId());

		if (!chatRoomService.deleteChatRoomByMemberIdAndChatRoomId(user.getMember().getId(), chatRoomId)) {
			return ResponseEntity.unprocessableEntity().body("채팅방을 나가는데 실패했습니다.");
		}

		return ResponseEntity.ok("성공");
	}

	@PatchMapping("/modify/{chatRoomId}")
	public ResponseEntity<?> modifyChatRoomName(
		@PathVariable Long chatRoomId,
		@AuthenticationPrincipal UserPrincipal user,
		@RequestBody final ModifyRequestBody modifyBody) {

		if (!chatRoomService.isIncludeMe(user.getMember().getId(), chatRoomId)) {
			return ResponseEntity.badRequest().body("권한이 없습니다.");
		}

		chatRoomService.modifyChatRoomName(user.getMember().getId(), chatRoomId, modifyBody.getChatRoomName());

		return ResponseEntity.ok("성공");
	}
}
