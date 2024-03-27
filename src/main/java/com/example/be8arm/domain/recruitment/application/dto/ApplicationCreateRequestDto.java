package com.example.be8arm.domain.recruitment.application.dto;

import java.time.LocalDateTime;
import java.time.LocalTime;

import com.example.be8arm.domain.member.member.entity.Member;
import com.fasterxml.jackson.annotation.JsonFormat;

import jakarta.validation.constraints.NotNull;
import lombok.*;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationCreateRequestDto {
	@NotNull
	private String writerId;

	@NonNull
	private String partnerId;

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

	// 작성자 정보를 가져오는 메서드

}
