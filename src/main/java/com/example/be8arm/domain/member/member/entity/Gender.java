package com.example.be8arm.domain.member.member.entity;

import lombok.Getter;

@Getter
public enum Gender {
	Female("여성"), Male("남성");

	private final String value;

	Gender(String value) {
		this.value = value;
	}
}
