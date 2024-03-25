package com.example.be8arm.domain.member.member.entity;

import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class MemberInfoDto {

	private String name; // 실명

	private String nickname;

	private String address;

	private String imgUrl; // 프로필 사진

	public MemberInfoDto(Member member) {
		this.name = member.getName();
		this.imgUrl = member.getImgUrl();
		this.nickname = member.getNickname();
		this.address = member.getAddress();
	}

}
