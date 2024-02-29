package com.example.be8arm.domain.recruitment.application.entity;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.global.TimeEntity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class Application extends TimeEntity {
	@ManyToOne
	@JoinColumn(name = "member_Id", nullable = false)
	private Member member;

	@Column(nullable = false)
	private String status;
}
