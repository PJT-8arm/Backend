package com.example.be8arm.domain.member.member.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.be8arm.domain.member.member.dto.MemberModifyDto;
import com.example.be8arm.global.TimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Member extends TimeEntity {

	@Column(nullable = false)
	private String username;  // 로그인 ID

	@Column(nullable = false)
	private String password;

	private String name; // 실명

	private String nickname;

	private String imgUrl; // 프로필 사진

	private String address;

	@OneToOne(mappedBy = "member", fetch = FetchType.LAZY)
	private Profile profile;

	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private List<String> roles = new ArrayList<>();

	public Member(String username, String name, String imgUrl, String nickname, Profile profile, String address) {
		this.username = username;
		this.name = name;
		this.imgUrl = imgUrl;
		this.nickname = nickname;
		this.profile = profile;
		this.address = address;
	}

	public boolean notHasProfile() {
		return this.profile == null;
	}

	@Transactional
	public void modify(MemberModifyDto dto) {
		if (this.nickname == null || (dto.getNickname() != null && !this.nickname.equals(dto.getNickname()))) {
			this.nickname = dto.getNickname();
		}

		if (this.name == null || (dto.getName() != null && !this.name.equals(dto.getName()))) {
			this.name = dto.getName();
		}

		if (dto.getPostPassword() != null && !dto.getPostPassword().isEmpty() && !dto.getPostPassword().isBlank()
			&& !this.password.equals(
			dto.getPostPassword())) {
			this.password = dto.getPostPassword();
		}

		if (this.imgUrl == null || (dto.getImgUrl() != null && !this.imgUrl.equals(dto.getImgUrl()))) {
			this.imgUrl = dto.getImgUrl();
		}
		if (this.address == null || (dto.getAddress() != null && !this.address.equals(dto.getAddress()))) {
			this.address = dto.getAddress();
		}
	}

}
