package com.example.be8arm.domain.member.member.entity;

import java.util.ArrayList;
import java.util.List;

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
}

