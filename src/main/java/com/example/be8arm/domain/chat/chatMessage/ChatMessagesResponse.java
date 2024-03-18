package com.example.be8arm.domain.chat.chatMessage;

import java.util.List;

import org.springframework.data.domain.Slice;

import com.example.be8arm.domain.chat.chatMessage.dto.ChatMessagesDto;

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
public class ChatMessagesResponse {
	List<ChatMessagesDto> messages;
	Boolean hasNext;

	public ChatMessagesResponse(Slice<ChatMessagesDto> chatMessageSlice) {
		this.messages = chatMessageSlice.getContent();
		this.hasNext = chatMessageSlice.hasNext();
	}
}
