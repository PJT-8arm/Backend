package com.example.be8arm.domain.recruitment.application.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.be8arm.domain.member.member.dto.MemberDto;
import com.example.be8arm.domain.member.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;

@Getter
@Setter
@NoArgsConstructor
@SuperBuilder
public class ApplicationDto {
	private Long id;
	private MemberDto writer;
	private MemberDto partner;
	private boolean isCanceled;
	private String status;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime createDate;
	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime modifyDate;

	private String title;

	private String content;

	@JsonFormat(pattern = "yyyy-MM-dd'T'HH:mm:ss")
	private LocalDateTime recruit_date;

	private String place;

	private String partnerGender;

	private Integer partnerAge;

	private String routine;

	private LocalTime duration;

	public ApplicationDto(Long id, MemberDto writer, MemberDto partner, boolean isCanceled, String status, LocalDateTime createDate, LocalDateTime modifyDate) {
		this.id = id;
		this.writer = writer;
		this.partner = partner;
		this.isCanceled = isCanceled;
		this.status = status;
		this.createDate = createDate;
		this.modifyDate = modifyDate;
		this.title = title;
		this.content = content;
		this.recruit_date = recruit_date;
		this.place = place;
		this.partnerGender = partnerGender;
		this.partnerAge = partnerAge;
		this.routine = routine;
		this.duration = duration;
	}
}
