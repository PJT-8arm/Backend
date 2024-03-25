package com.example.be8arm.domain.chat.chatRoom.dto;

import java.time.LocalDateTime;

import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@Builder
@ToString
public class ChatRoomDetailDto {
	private Long chatRoomId;
	private String chatRoomName;
	private String imgUrl;
	private String lastMessageContent;
	private String lastMessageSender;
	private LocalDateTime lastMessageDate;
	private Long unreadMessagesCount;

	public ChatRoomDetailDto(Long chatRoomId, String chatRoomName, String imgUrl, String lastMessageContent,
		String lastMessageSender, LocalDateTime lastMessageDate, Long unreadMessagesCount) {
		this.chatRoomId = chatRoomId;
		this.chatRoomName = chatRoomName;
		this.imgUrl = imgUrl;
		this.lastMessageContent = lastMessageContent;
		this.lastMessageSender = lastMessageSender;
		this.lastMessageDate = lastMessageDate;
		this.unreadMessagesCount = unreadMessagesCount;
	}
}
