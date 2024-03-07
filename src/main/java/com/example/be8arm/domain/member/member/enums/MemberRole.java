package com.example.be8arm.domain.member.member.enums;

import lombok.Getter;

@Getter
public enum MemberRole {
	USER("ROLE_USER"),
	ADMIN("ROLE_ADMIN");

	private final String value;

	MemberRole(String value) {
		this.value = value;
	}
}
