package com.example.be8arm.domain.chat.chatRoom.entity;

import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import com.example.be8arm.domain.member.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Column;
import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.EntityListeners;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.MapsId;
import jakarta.persistence.PrePersist;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@Builder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@EntityListeners(AuditingEntityListener.class)
public class ChatRoomMember {
	@EmbeddedId
	private ChatRoomMemberId id;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("chatRoomId") // ChatMemberId 내 chatRoomId 매핑
	@JoinColumn(name = "chat_room_id")
	@JsonIgnore // 무한 재귀 방지
	private ChatRoom chatRoom;

	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("memberId") // ChatMemberId 내 memberId 매핑
	@JoinColumn(name = "member_id")
	private Member member;

	@Column(length = 50)
	private String chatRoomName; //사용자마다 채팅방 이름을 다르게 설정할 수 있도록

	private String imgUrl;

	@Column(nullable = false)
	private Long lastViewMessageId;

	public void setChatRoomName(String chatRoomName) {
		this.chatRoomName = chatRoomName;
	}

	@PrePersist
	public void prePersist() {
		if (lastViewMessageId == null) {
			lastViewMessageId = 0L;
		}
	}
}
