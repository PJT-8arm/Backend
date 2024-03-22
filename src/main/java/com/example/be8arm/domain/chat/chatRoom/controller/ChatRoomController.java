package com.example.be8arm.domain.chat.chatRoom.controller;

import java.util.List;

import org.springframework.data.domain.Slice;
import org.springframework.http.HttpStatus;
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

import com.example.be8arm.domain.chat.chatMessage.dto.ChatMessagesDto;
import com.example.be8arm.domain.chat.chatMessage.service.ChatMessageService;
import com.example.be8arm.domain.chat.chatRoom.controller.request.ModifyRequestBody;
import com.example.be8arm.domain.chat.chatRoom.controller.request.WriteRequestBody;
import com.example.be8arm.domain.chat.chatRoom.dto.ChatRoomDetailDto;
import com.example.be8arm.domain.chat.chatRoom.dto.ChatRoomInfoDto;
import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoomMember;
import com.example.be8arm.domain.chat.chatRoom.service.ChatRoomService;
import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.member.service.MemberService;
import com.example.be8arm.global.security.UserPrincipal;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/api/chat/room")
@RequiredArgsConstructor
@Slf4j
@Tag(name = "Chat", description = "Chat API")
public class ChatRoomController {
	private final ChatRoomService chatRoomService;
	private final ChatMessageService chatMessageService;
	private final MemberService memberService;

	@GetMapping("/{roomId}")
	@Operation(summary = "채팅방 정보 조회")
	@ApiResponse(responseCode = "200", description = "채팅방 정보 반환", content = {
		@Content(mediaType = "application/json", schema = @Schema(implementation = ChatRoomInfoDto.class))})
	@ApiResponse(responseCode = "404", description = "채팅방을 찾을 수 없음")
	@ApiResponse(responseCode = "403", description = "접근 권한이 없음")
	public ResponseEntity<?> showRoom(@PathVariable final long roomId, @AuthenticationPrincipal UserPrincipal user) {
		if (!chatRoomService.existsById(roomId)) {
			return ResponseEntity.notFound().build();
		}
		if (!chatRoomService.isIncludeMe(user.getMember().getId(), roomId)) {
			return ResponseEntity.status(HttpStatus.FORBIDDEN).body("접근 권한이 없습니다.");
		}

		ChatRoomMember chatRoomMember = chatRoomService.findChatRoomMemberByChatRoomIdAndMemberId(
			user.getMember().getId(), roomId);

		ChatRoomInfoDto chatRoomInfoDto = new ChatRoomInfoDto(chatRoomMember);

		return ResponseEntity.ok(chatRoomInfoDto);
	}

	@GetMapping("/{roomId}/messages")
	@Operation(summary = "채팅방 메세지 30개씩 반환")
	@ApiResponse(responseCode = "200", description = "채팅메세지 반환", content = {
		@Content(mediaType = "application/json", schema = @Schema(implementation = ChatMessagesDto.class))})
	public ResponseEntity<?> showMessages(@PathVariable long roomId,
		@RequestParam(value = "lastId", required = false) Long lastMessageId,
		@RequestParam(value = "page", defaultValue = "0") int page,
		@RequestParam(value = "size", defaultValue = "30") int size) {

		if (lastMessageId == null) {
			lastMessageId = chatMessageService.findLastChatMessageIdInChatRoom(roomId) + 1L;
		}

		Slice<ChatMessagesDto> messageSlice = chatMessageService.findMessagesBeforeId(roomId, lastMessageId, 0,
			size);

		return ResponseEntity.ok(messageSlice);
	}

	@GetMapping("/list")
	@Operation(summary = "채팅방 목록 조회")
	@ApiResponse(responseCode = "200", description = "채팅방 목록 반환", content = {
		@Content(mediaType = "application/json", schema = @Schema(implementation = ChatRoomDetailDto.class))})
	public ResponseEntity<?> showList2(@AuthenticationPrincipal UserPrincipal user) {
		List<ChatRoomDetailDto> chatRooms = chatRoomService.showChatRoomList(user.getMember().getId());
		return ResponseEntity.ok(chatRooms);
	}

	@PostMapping("/{roomId}/write")
	@Operation(summary = "메세지 전송")
	public ResponseEntity<?> write(
		//TODO roomId를 request에 넣을지 말지
		@PathVariable final long roomId, @AuthenticationPrincipal UserPrincipal user,
		@RequestBody final WriteRequestBody requestBody) {
		chatMessageService.writeAndSend(roomId, user.getMember().getName(), requestBody.getContent(), "created",
			user.getMember().getId());

		return ResponseEntity.ok("성공");
	}

	@GetMapping("/make/{theirName}")
	@Operation(summary = "채팅방 생성")
	@ApiResponse(responseCode = "200", description = "채팅방 정보 반환", content = {
		@Content(mediaType = "application/json", schema = @Schema(implementation = Long.class))})
	public ResponseEntity<?> makeChatRoom(@AuthenticationPrincipal UserPrincipal user, @PathVariable String theirName //
	) {
		Member myMember = user.getMember();
		Member theirMember = memberService.findByName(theirName);

		Long chatRoomId;
		//이미 존재 하는 방일 때 0보다 큰값이 return됨
		if ((chatRoomId = chatRoomService.findChatRoom(myMember.getId(), theirMember.getId())) <= 0) {
			chatRoomId = chatRoomService.makeChatRoom(myMember, theirMember);
			chatMessageService.writeAndSend(chatRoomId, myMember.getName(), "생성", "created", myMember.getId());
		} else {
			chatRoomId = chatRoomService.findChatRoom(myMember.getId(), theirMember.getId());
		}
		
		return ResponseEntity.ok(chatRoomId);
	}

	@DeleteMapping("/exit/{chatRoomId}")
	@Operation(summary = "채팅방 나가기")
	public ResponseEntity<?> exitChatRoom(@PathVariable Long chatRoomId, @AuthenticationPrincipal UserPrincipal user) {

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
	@Operation(summary = "채팅방 이름 수정", description = "지정된 채팅방의 이름을 수정합니다.")
	@ApiResponse(responseCode = "200", description = "성공적으로 수정됨")
	@ApiResponse(responseCode = "400", description = "권한이 없습니다.", content = @Content(mediaType = "application/json", schema = @Schema(implementation = String.class)))
	public ResponseEntity<?> modifyChatRoomName(@PathVariable Long chatRoomId,
		@AuthenticationPrincipal UserPrincipal user, @RequestBody final ModifyRequestBody modifyBody) {

		if (!chatRoomService.isIncludeMe(user.getMember().getId(), chatRoomId)) {
			return ResponseEntity.badRequest().body("권한이 없습니다.");
		}

		chatRoomService.modifyChatRoomName(user.getMember().getId(), chatRoomId, modifyBody.getChatRoomName());

		return ResponseEntity.ok("성공");
	}
}
