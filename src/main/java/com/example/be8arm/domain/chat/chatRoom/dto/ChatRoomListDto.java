package com.example.be8arm.domain.chat.chatRoom.dto;

import java.time.LocalDateTime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomListDto {
	private Long chatRoomId;
	private String name;
	private Long latestMessageId;
	private String lastMessage;
	private LocalDateTime lastDateTime;
	private String lastWriter;
	private String imgUrl;
	private int unreadMessagesCount;

	public ChatRoomListDto(Object[] result) {
		this.chatRoomId = (Long)result[0];
		this.name = (String)result[1];
		this.latestMessageId = (Long)result[2];
		this.lastMessage = (String)result[3];
		this.lastDateTime = (LocalDateTime)result[4];
		this.lastWriter = (String)result[5];
		this.imgUrl = (String)result[6];
		this.unreadMessagesCount = (int)result[7];
	}
}
