package com.example.be8arm.domain.recruitment.application.service;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.member.member.repository.MemberRepository;
import com.example.be8arm.domain.recruitment.application.dto.*;
import com.example.be8arm.domain.recruitment.application.entity.Application;
import com.example.be8arm.domain.recruitment.application.repository.ApplicationRepository;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentListDetailResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.entity.Recruitment;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class ApplicationService {

	private final MemberRepository memberRepository;
	private final ApplicationRepository applicationRepository;

	@Transactional
	public ApplicationCreateResponseDto createApplication(Member member, String username, ApplicationCreateRequestDto requestDto) {

		Member writer = null;
		if (requestDto.getWriterId() != null) {
			writer = memberRepository.findByUsername(requestDto.getWriterId())
				.orElseThrow(() -> new IllegalArgumentException("작성자를 찾을 수 없습니다: " + requestDto.getWriterId()));
		}

		// 파트너를 파트너ID로 찾습니다.
		Member partner = null;
		if (requestDto.getPartnerId() != null) {
			partner = memberRepository.findByUsername(requestDto.getPartnerId())
				.orElseThrow(() -> new IllegalArgumentException("파트너를 찾을 수 없습니다: " + requestDto.getPartnerId()));
		}

		// 신청서를 생성합니다.
		Application application = Application.builder()
			.member(member)
			.writer(writer)
			.partner(partner)
			.status(requestDto.getStatus())
			.title(requestDto.getTitle())
			.content(requestDto.getContent())
			.recruit_date(requestDto.getRecruit_date())
			.place(requestDto.getPlace())
			.partnerGender(requestDto.getPartnerGender())
			.partnerAge(requestDto.getPartnerAge())
			.routine(requestDto.getRoutine())
			.duration(requestDto.getDuration())
			.build();

		// 생성된 신청서를 저장합니다.
		applicationRepository.save(application);

		return new ApplicationCreateResponseDto(application);
	}

	public List<ApplicationListDto> findApplicationList() {
		List<Application> applications = applicationRepository.findAll();
		return applications.stream()
			.map(ApplicationListDto::new)
			.collect(Collectors.toList());
	}

	public ApplicationListResponseDto findApplication(Long id) {
		Application application = applicationRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("해당 약속이 존재하지 않습니다."));

		return new ApplicationListResponseDto(application);
	}

	public List<ApplicationListResponseDto> getAllApplicationDetails() {
		List<Application> applications = applicationRepository.findAll();
		return applications.stream()
			.map(ApplicationListResponseDto::new)
			.collect(Collectors.toList());
	}
}
