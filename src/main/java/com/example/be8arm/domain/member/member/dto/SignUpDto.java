package com.example.be8arm.domain.member.member.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.be8arm.domain.member.member.entity.Member;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
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
}
