package com.example.be8arm.domain.chat.chatRoom.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@ToString
public class ChatRoomMemberInfoDto {
	private Long id;
	private String name;
	private String imgUrl;
}
