package com.example.be8arm.domain.member.mypage.dto;

import com.example.be8arm.domain.member.member.entity.Gender;
import com.example.be8arm.domain.member.member.entity.Profile;

import lombok.Data;

@Data
public class ProfileDto {
	private Integer age;

	private Gender gender;

	private Integer benchPress;

	private Integer deadLift;

	private Integer squat;

	private Integer totalWeight;

	public ProfileDto(Profile profile) {
		this.age = profile.getAge();
		this.gender = profile.getGender();
		this.benchPress = profile.getBenchPress();
		this.deadLift = profile.getDeadLift();
		this.squat = profile.getSquat();
		this.totalWeight = profile.getTotalWeight();
	}

}

