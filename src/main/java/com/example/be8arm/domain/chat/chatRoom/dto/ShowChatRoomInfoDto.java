package com.example.be8arm.domain.chat.chatRoom.dto;

import java.util.List;
import java.util.Map;

import com.example.be8arm.domain.chat.chatRoom.entity.ChatRoomMember;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class ShowChatRoomInfoDto {
	private Long chatRoomId;
	private String chatRoomName; //방 이름
	private Map<String,String> imgUrl; //<이름, 프로필사진>

	//생성자
}
