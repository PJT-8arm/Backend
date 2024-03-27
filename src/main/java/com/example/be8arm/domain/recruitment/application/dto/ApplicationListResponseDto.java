package com.example.be8arm.domain.recruitment.application.dto;

import com.example.be8arm.domain.member.member.entity.MemberInfoDto;
import com.example.be8arm.domain.recruitment.application.entity.Application;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationListResponseDto {

	private MemberInfoDto writerInfoDto;

	private MemberInfoDto partnerInfoDto;

	private boolean isCanceled;

	private String status;

	public ApplicationListResponseDto(Application application) {
		this.writerInfoDto = new MemberInfoDto(application.getWriter());
		this.partnerInfoDto = application.getPartner() != null ? new MemberInfoDto(application.getPartner()) : null;
		this.isCanceled = application.isCanceled();
		this.status = application.getStatus();
	}
}
