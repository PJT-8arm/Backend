package com.example.be8arm.domain.member.member.dto;

import com.example.be8arm.domain.member.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class MemberDto {

	private Long id;
	private String username;
	private String nickname;
	private String name;
	private String imgUrl;

	static public MemberDto toDto(Member member) {
		return MemberDto.builder()
			.id(member.getId())
			.username(member.getUsername())
			.nickname(member.getNickname())
			.name(member.getName())
			.imgUrl(member.getImgUrl()).build();
	}

	public Member toEntity() {
		return Member.builder()
			.id(id)
			.username(username)
			.nickname(nickname)
			.name(name)
			.imgUrl(imgUrl)
			.build();
	}
}
