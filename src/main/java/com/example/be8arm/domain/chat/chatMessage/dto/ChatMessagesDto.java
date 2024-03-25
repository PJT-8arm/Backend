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
	private Long id;

	private LocalDateTime createDate;

	private String writerName;

	private String content;

	private Long senderId;

	public ChatMessagesDto(Long id, LocalDateTime createDate, String writerName, String content, Long senderId) {
		this.id = id;
		this.createDate = createDate;
		this.writerName = writerName;
		this.content = content;
		this.senderId = senderId;
	}
}
