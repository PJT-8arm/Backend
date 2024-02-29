package com.example.be8arm.domain.member.member.entity;

import com.example.be8arm.global.TimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
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

	private Gender gender;

	private Integer benchPress;

	private Integer deadLift;

	private Integer squat;

	private Integer totalWeight;
}
