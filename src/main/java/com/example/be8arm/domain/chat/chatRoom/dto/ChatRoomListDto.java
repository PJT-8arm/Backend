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
}
