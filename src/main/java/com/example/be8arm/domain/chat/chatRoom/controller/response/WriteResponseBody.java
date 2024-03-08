package com.example.be8arm.domain.chat.chatRoom.controller.response;

import com.example.be8arm.domain.chat.chatMessage.entity.ChatMessage;

import lombok.Getter;

@Getter
public class WriteResponseBody {
	private ChatMessage message;
	private String chatRoomName;
	private String eventType;
	private String imgUrl;

	public WriteResponseBody(ChatMessage chatMessage, String eventType) {
		this.message = chatMessage;
		this.eventType = eventType;
	}

	public void setChatRoomName(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

	public void setImgUrl(String imgUrl) {
		this.imgUrl = imgUrl;
	}
}
