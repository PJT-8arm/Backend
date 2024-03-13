package com.example.be8arm.domain.member.member.entity;

import java.util.ArrayList;
import java.util.List;

import org.springframework.transaction.annotation.Transactional;

import com.example.be8arm.domain.member.member.dto.SignUpDto;
import com.example.be8arm.global.TimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@EqualsAndHashCode(of = "id")
public class Member extends TimeEntity {

	@Column(nullable = false)
	private String username;  // 로그인 ID

	@Column(nullable = false)
	private String password;

	private String name; // 실명

	private String nickname;

	private String imgUrl; // 프로필 사진

	@OneToOne
	private Profile profile;

	@ElementCollection(fetch = FetchType.EAGER)
	@Builder.Default
	private List<String> roles = new ArrayList<>();

	public Member(String username, String name, String imgUrl, String nickname, Profile profile) {
		this.username = username;
		this.name = name;
		this.imgUrl = imgUrl;
		this.nickname = nickname;
		this.profile = profile;
	}

	public boolean notHasProfile() {
		return this.profile == null;
	}

	@Transactional
	public void createProfile() {
		this.profile = new Profile(this);
	}

	@Transactional
	public Member modify(SignUpDto dto) {
		if (!this.nickname.equals(dto.getNickname())) {
			this.nickname = dto.getNickname();
		}

		if (!this.name.equals(dto.getName())) {
			this.name = dto.getName();
		}

		if (!this.password.equals(dto.getPassword())) {
			this.password = dto.getPassword();
		}

		if (!this.imgUrl.equals(dto.getImgUrl())) {
			this.imgUrl = dto.getImgUrl();
		}
		return this;
	}
}

