package com.example.be8arm.domain.chat.chatRoom.dto;

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
public class ChatRoomInfoDto {
	private Long memberId;
	private Long chatRoomId;
	private String chatRoomName; //방 이름
	private String imgUrl; //<이름, 프로필사진>

	//생성자
	public ChatRoomInfoDto(ChatRoomMember chatRoomMember) {
		this.memberId = chatRoomMember.getId().getMemberId();
		this.chatRoomId = chatRoomMember.getId().getChatRoomId();
		this.chatRoomName = chatRoomMember.getChatRoomName();
		this.imgUrl = chatRoomMember.getImgUrl();
	}
}
