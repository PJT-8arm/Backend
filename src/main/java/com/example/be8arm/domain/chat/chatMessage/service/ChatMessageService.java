package com.example.be8arm.domain.chat.chatMessage.service;

import java.util.List;
import java.util.NoSuchElementException;

import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.be8arm.domain.chat.chatMessage.entity.ChatMessage;
import com.example.be8arm.domain.chat.chatMessage.repository.ChatMessageRepository;
import com.example.be8arm.domain.chat.chatRoom.controller.response.WriteResponseBody;
import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoom;
import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoomMember;
import com.example.be8arm.domain.chat.chatRoom.repository.ChatRoomMemberRepository;
import com.example.be8arm.domain.chat.chatRoom.repository.ChatRoomRepository;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ChatMessageService {
	private final ChatMessageRepository chatMessageRepository;
	private final ChatRoomMemberRepository chatRoomMemberRepository;
	private final SimpMessagingTemplate messagingTemplate;
	private final ChatRoomRepository chatRoomRepository;

	@Transactional
	public ChatMessage writeAndSend(long roomId, String writerName, String content, String eventType, long senderId) {
		ChatRoom chatRoom = chatRoomRepository.findById(roomId)
			.orElseThrow(() -> new NoSuchElementException("채팅방이 존재하지 않습니다."));
		;

		ChatMessage chatMessage = writeMessage(chatRoom, writerName, content, senderId);
		sendMessageToRoomAndList(chatRoom, new WriteResponseBody(chatMessage, eventType));

		return chatMessage;
	}

	@Transactional
	public ChatMessage writeMessage(ChatRoom chatRoom, String writerName, String content, long senderId) {
		ChatMessage chatMessage = ChatMessage
			.builder()
			.chatRoom(chatRoom)
			.writerName(writerName)
			.content(content)
			.senderId(senderId)
			.build();

		chatMessage = chatMessageRepository.save(chatMessage);

		return chatMessage;
	}

	public void sendMessageToRoomAndList(ChatRoom chatRoom, WriteResponseBody writeBody) {
		//채팅방에 메세지 보내기
		messagingTemplate.convertAndSend("/topic/chat/room/" + chatRoom.getId() + "/message", writeBody);

		List<ChatRoomMember> chatRoomMembers = chatRoomMemberRepository.findChatRoomMemberByChatRoomId(
			chatRoom.getId());
		//채팅 방에 있는 모든 멤버의 list에 메세지 보내기
		for (ChatRoomMember ChatRoomMember : chatRoomMembers) {
			writeBody.setChatRoomName(ChatRoomMember.getChatRoomName());
			writeBody.setImgUrl(ChatRoomMember.getImgUrl());
			messagingTemplate.convertAndSend("/topic/chat/room/list/"
				+ ChatRoomMember.getId().getMemberId() //memberId
				+ "/message", writeBody);
		}

	}

	public List<ChatMessage> findByChatRoomIdAndIdAfter(long roomId, long afterId) {
		return chatMessageRepository.findByChatRoomIdAndIdAfter(roomId, afterId);
	}

}
