package com.example.be8arm.domain.member.member.dto;

import java.util.ArrayList;
import java.util.List;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.mypage.dto.ProfileDto;

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
	private ProfileDto profile;
	@Builder.Default
	private List<String> roles = new ArrayList<>();

	static public MemberDto toDto(Member member) {
		ProfileDto profileDto;
		if (member.notHasProfile()) {
			profileDto = null;
		} else {
			profileDto = new ProfileDto(member.getProfile());
		}
		return MemberDto.builder()
			.id(member.getId())
			.username(member.getUsername())
			.nickname(member.getNickname())
			.name(member.getName())
			.imgUrl(member.getImgUrl())
			.roles(member.getRoles())
			.profile(profileDto).build();
	}

	public Member toEntity() {
		return Member.builder()
			.id(id)
			.username(username)
			.nickname(nickname)
			.name(name)
			.imgUrl(imgUrl)
			.roles(roles)
			.profile(profile.toEntity())
			.build();
	}
}
