package com.example.be8arm.domain.recruitment.application.dto;

import com.example.be8arm.domain.member.member.entity.Member;

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
}
