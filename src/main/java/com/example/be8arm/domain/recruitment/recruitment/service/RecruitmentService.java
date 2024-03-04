package com.example.be8arm.domain.recruitment.recruitment.service;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentCreateRequestDto;
import com.example.be8arm.domain.recruitment.recruitment.dto.RecruitmentCreateResponseDto;
import com.example.be8arm.domain.recruitment.recruitment.entity.Recruitment;
import com.example.be8arm.domain.recruitment.recruitment.repository.RecruitmentRepository;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
