package com.example.be8arm.domain.chat.chatMessage.dto;

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
public class ChatMessagesDto {

	private LocalDateTime createDate;

	private String writerName;

	private String content;

	private Long senderId;

	public ChatMessagesDto(LocalDateTime createDate, String writerName, String content, Long senderId) {
		this.createDate = createDate;
		this.writerName = writerName;
		this.content = content;
		this.senderId = senderId;
	}
}
