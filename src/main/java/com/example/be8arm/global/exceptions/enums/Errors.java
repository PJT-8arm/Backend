package com.example.be8arm.global.exceptions.enums;

import lombok.Getter;

@Getter
public enum Errors {
	NotFoundUser("유저를 찾을 수 없습니다."),
	NotMatchUser("잘못된 사용자 입니다."),
	NotMatchPassword("잘못된 비밀번호입니다.");

	private String msg;

	Errors(String msg){
		this.msg = msg;
	}
}
