package com.example.be8arm.domain.chat.chatRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ChatRoomMemberInfoDto {
	Long id;
	String name;
	String imgUrl;
}
