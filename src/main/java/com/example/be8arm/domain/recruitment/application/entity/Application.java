package com.example.be8arm.domain.recruitment.application.entity;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.recruitment.recruitment.entity.Recruitment;
import com.example.be8arm.global.TimeEntity;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
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
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	private Member member;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "writer_Id", nullable = false)
	private Member writer;

	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "partner_Id")
	private Member partner;

	private boolean isCanceled;

	@Column(nullable = false)
	private String status;

	private String title;

	private String content;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime recruit_date;

	private String place;

	private String partnerGender;

	private Integer partnerAge;

	private String routine;

	private LocalTime duration;
}
