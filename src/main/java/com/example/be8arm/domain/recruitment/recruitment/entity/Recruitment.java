package com.example.be8arm.domain.recruitment.recruitment.entity;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.global.TimeEntity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@Setter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@SuperBuilder
public class Recruitment extends TimeEntity {
	@ManyToOne(fetch = FetchType.EAGER)
	@JoinColumn(name = "member_id")
	private Member member;

	private String title;

	private String content;

	private LocalDateTime recruit_date;

	private String place;

	private String partnerGender;

	private Integer partnerAge;

	private String routine;

	private LocalTime duration;

}
