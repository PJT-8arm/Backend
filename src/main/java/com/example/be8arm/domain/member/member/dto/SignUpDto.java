package com.example.be8arm.domain.member.member.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.be8arm.domain.member.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class SignUpDto {

	private String username;
	private String password;
	private String nickname;
	private String name;
	private String imgUrl;
	private List<String> roles = new ArrayList<>();

	public Member toEntity(String encodedPassword, List<String> roles) {

		return Member.builder()
			.username(username)
			.password(encodedPassword)
			.nickname(nickname)
			.name(name)
			.imgUrl(imgUrl)
			.roles(roles)
			.build();
	}

	public SignUpDto(Member member) {
		this.username = member.getUsername();
		this.nickname = member.getNickname();
		this.name = member.getName();
		this.imgUrl = member.getImgUrl();
	}
}
