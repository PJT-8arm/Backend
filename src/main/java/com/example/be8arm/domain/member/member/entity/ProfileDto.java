package com.example.be8arm.domain.member.member.entity;

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
