package com.example.be8arm.domain.recruitment.recruitment.service;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentCreateRequestDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentCreateResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentListDetailResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentListResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.entity.Recruitment;
import com.example.be8arm.domain.recruitment.recruitment.repository.RecruitmentRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class RecruitmentService {

    private final RecruitmentRepository recruitmentRepository;

    @Transactional
    public RecruitmentCreateResponseDto addRecruitment(Member member, RecruitmentCreateRequestDto recruitmentCreateRequestDto) {
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
                .map(recruitment -> new RecruitmentListResponseDto(
                        recruitment.getMember(),
                        recruitment.getTitle(),
                        recruitment.getRecruit_date(),
                        recruitment.getPartnerGender(),
                        recruitment.getPartnerAge(),
                        recruitment.getRoutine()
                        ))
                .toList();
        return recruitments;
    }

    public RecruitmentListDetailResponseDto findRecruitment(Long id){
        Recruitment recruitment = recruitmentRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("해당 모집 글이 존재하지 않습니다."));

        return new RecruitmentListDetailResponseDto(recruitment);
    }
}
