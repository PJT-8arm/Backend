package com.example.be8arm.domain.recruitment.application.dto;

import com.example.be8arm.domain.recruitment.application.entity.Application;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ApplicationCreateResponseDto {

	private Long id;

	private String writerId;

	private String partnerId;

	private boolean isCanceled;

	private String status;

	// 필요한 경우에 writerId를 Long 타입으로 반환하는 메서드
	public Long getWriterIdAsLong() {
		return Long.parseLong(writerId);
	}

	// 필요한 경우에 partnerId를 Long 타입으로 반환하는 메서드
	public Long getPartnerIdAsLong() {
		return Long.parseLong(partnerId);
	}

	public ApplicationCreateResponseDto(Application application) {
		this.id = application.getId();
		this.writerId = String.valueOf(application.getWriter().getId());
		this.partnerId = application.getPartner() != null ? String.valueOf(application.getPartner().getId()) : null;
		this.isCanceled = application.isCanceled();
		this.status = application.getStatus();
	}
}
