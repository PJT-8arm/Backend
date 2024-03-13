package com.example.be8arm.domain.member.member.entity;

import com.example.be8arm.domain.member.mypage.dto.ProfileDto;
import com.example.be8arm.global.TimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Getter
@SuperBuilder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Profile extends TimeEntity {
	@OneToOne
	private Member member;

	private Integer age;

	@Builder.Default
	private Gender gender = Gender.Male;

	private Integer benchPress;

	private Integer deadLift;

	private Integer squat;

	private Integer totalWeight;

	public void modify(ProfileDto profileDto) {
		this.age = profileDto.getAge();
		this.gender = profileDto.getGender();
		this.benchPress = profileDto.getBenchPress();
		this.deadLift = profileDto.getDeadLift();
		this.squat = profileDto.getSquat();
		this.totalWeight = profileDto.getTotalWeight();
	}
}
