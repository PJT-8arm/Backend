package com.example.be8arm.domain.recruitment.recruitment.service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

import com.example.be8arm.domain.member.member.entity.MemberInfoDto;
import com.example.be8arm.domain.recruitment.recruitment.entity.RecruitmentDto;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentCreateRequestDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentCreateResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentListDetailResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentListResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentUpdateResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.entity.Recruitment;
import com.example.be8arm.domain.recruitment.recruitment.repository.RecruitmentRepository;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RecruitmentService {

	private final RecruitmentRepository recruitmentRepository;

	@Transactional
	public RecruitmentCreateResponseDto addRecruitment(Member member,
		RecruitmentCreateRequestDto recruitmentCreateRequestDto) {
		// todo
        /*
            1. request에서 다 가져와서 response에 넣은 다음 member도 넣어준다.
         */
		Recruitment recruitment = Recruitment.builder()
			.member(member)
			.title(recruitmentCreateRequestDto.getTitle())
			.content(recruitmentCreateRequestDto.getContent())
			.recruit_date(recruitmentCreateRequestDto.getRecruit_date())
			.place(recruitmentCreateRequestDto.getPlace())
			.partnerGender(recruitmentCreateRequestDto.getPartnerGender())
			.partnerAge(recruitmentCreateRequestDto.getPartnerAge())
			.routine(recruitmentCreateRequestDto.getRoutine())
			.duration(recruitmentCreateRequestDto.getDuration())
			.build();
		recruitmentRepository.save(recruitment);

		return new RecruitmentCreateResponseDto(recruitment);
	}

    public List<RecruitmentListResponseDto> findRecruitmentList() {
        List<RecruitmentListResponseDto> recruitments = recruitmentRepository.findAll()
                .stream()
                .map(RecruitmentListResponseDto::new)
                .toList();
        return recruitments;
    }

	public RecruitmentListDetailResponseDto findRecruitment(Long id) {
		Recruitment recruitment = recruitmentRepository.findById(id)
			.orElseThrow(() -> new NoSuchElementException("해당 모집 글이 존재하지 않습니다."));

		return new RecruitmentListDetailResponseDto(recruitment);
	}

	@Transactional
	public RecruitmentUpdateResponseDto updateRecruitment(Long id, Member member,
		RecruitmentCreateRequestDto recruitmentUpdateRequestDto) {

		Recruitment existingRecruitment = recruitmentRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("해당 ID의 모집 공고를 찾을 수 없습니다."));

		existingRecruitment.setTitle(recruitmentUpdateRequestDto.getTitle());
		existingRecruitment.setContent(recruitmentUpdateRequestDto.getContent());
		existingRecruitment.setRecruit_date(recruitmentUpdateRequestDto.getRecruit_date());
		existingRecruitment.setPlace(recruitmentUpdateRequestDto.getPlace());
		existingRecruitment.setPartnerGender(recruitmentUpdateRequestDto.getPartnerGender());
		existingRecruitment.setPartnerAge(recruitmentUpdateRequestDto.getPartnerAge());
		existingRecruitment.setRoutine(recruitmentUpdateRequestDto.getRoutine());
		existingRecruitment.setDuration(recruitmentUpdateRequestDto.getDuration());

		// 기존 엔터티를 업데이트하고 저장
		recruitmentRepository.save(existingRecruitment);

		return new RecruitmentUpdateResponseDto(existingRecruitment);
	}

	@Transactional
	public void deleteRecruitment(Member member, Long id) {
		Recruitment existingRecruitment = recruitmentRepository.findById(id)
			.orElseThrow(() -> new RuntimeException("해당 ID의 모집 공고를 찾을 수 없습니다."));

		recruitmentRepository.delete(existingRecruitment);
	}

	public List<RecruitmentListResponseDto> findMyRecruitmentList(Member member) {
		List<Recruitment> recruitments = recruitmentRepository.findAllByMember(member);

		return recruitments.stream()
				.map(recruitment -> new RecruitmentListResponseDto(
						new MemberInfoDto(recruitment.getMember()), // MemberInfoDto 생성자에 필요한 파라미터를 전달
						new RecruitmentDto(recruitment) // RecruitmentDto 생성자에 필요한 파라미터를 전달
				))
				.collect(Collectors.toList());
	}
}
