package com.example.be8arm.domain.member.mypage.dto;

import com.example.be8arm.domain.member.member.entity.Gender;
import com.example.be8arm.domain.member.member.entity.Profile;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ProfileDto {
	private Long id;

	private Integer age;

	private Gender gender;

	private Integer benchPress;

	private Integer deadLift;

	private Integer squat;

	private Integer totalWeight;

	public ProfileDto(Profile profile) {
		this.id = profile.getId();
		this.age = profile.getAge();
		this.gender = profile.getGender();
		this.benchPress = profile.getBenchPress();
		this.deadLift = profile.getDeadLift();
		this.squat = profile.getSquat();
		this.totalWeight = profile.getTotalWeight();
	}

	public Profile toEntity() {
		return Profile.builder()
			.age(age)
			.benchPress(benchPress)
			.deadLift(deadLift)
			.gender(gender)
			.squat(squat)
			.totalWeight(totalWeight)
			.build();
	}

}

