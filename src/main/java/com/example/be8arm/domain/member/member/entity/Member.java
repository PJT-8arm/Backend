package com.example.be8arm.domain.member.member.entity;

import com.example.be8arm.global.TimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
public class Member extends TimeEntity {

	private String loginId;  // 로그인 ID

	private String password;

	private String name; // 실명

	private String nickname;

	private String imgUrl; // 프로필 사진

	@OneToOne
	private Profile profile;
}
