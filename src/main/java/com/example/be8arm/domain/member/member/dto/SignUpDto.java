package com.example.be8arm.domain.member.member.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.be8arm.domain.member.member.entity.Member;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Pattern;
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

	@NotEmpty(message = "회원님의 로그인Id를 적어주세요.")
	@Pattern(
		regexp = "^(?=.*[a-z])[a-z0-9]{6,20}$",
		message = "id는 소문자 하나이상있어야하고, 6자~20자여야합니다."
	)
	private String username;

	@NotBlank(message = "회원님의 비밀번호를 적어주세요.")
	@Pattern(
		regexp = "(?=.*[0-9])(?=.*[a-z])(?=.*\\W)(?=\\S+$).{8,16}",
		message = "영문자(A-Z, a-z), 숫자, 특수문자로 구성된 8~16자입니다."
	)
	private String password;
	private String checkPassword;
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
