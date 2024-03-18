package com.example.be8arm.domain.chat.chatMessage.dto;

import java.util.List;

import org.springframework.data.domain.Slice;

import com.example.be8arm.domain.chat.chatMessage.entity.ChatMessage;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@ToString
public class ChatMessagesDto {
	List<ChatMessage> messages;
	Boolean hasNext;

	public ChatMessagesDto(Slice<ChatMessage> chatMessageSlice) {
		this.messages = chatMessageSlice.getContent();
		this.hasNext = chatMessageSlice.hasNext();
	}
}
