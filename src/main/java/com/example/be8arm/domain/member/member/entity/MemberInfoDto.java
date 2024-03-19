package com.example.be8arm.domain.member.member.entity;

import com.example.be8arm.domain.member.member.dto.MemberModifyDto;
import com.example.be8arm.global.TimeEntity;
import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.SuperBuilder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
@SuperBuilder
@EqualsAndHashCode(of = "id")
public class MemberInfoDto {

	private String name; // 실명

	private String nickname;

	private String imgUrl; // 프로필 사진


	public MemberInfoDto(Member member) {
		this.name = member.getName();
		this.imgUrl = member.getImgUrl();
		this.nickname = member.getNickname();

	}

}
