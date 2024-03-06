package com.example.be8arm.domain.recruitment.recruitment.dto;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.recruitment.recruitment.entity.Recruitment;
import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RecruitmentCreateResponseDto {

    private Long id;

    private Member member;

    private String title;

    private String content;

    private LocalDateTime recruit_date;

    private String place;

    private String partnerGender;

    private Integer partnerAge;

    private String routine;

    private LocalTime duration;

    public RecruitmentCreateResponseDto(Recruitment recruitment) {
        this.id = recruitment.getId();
        this.member = recruitment.getMember();
        this.title = recruitment.getTitle();
        this.content = recruitment.getContent();
        this.recruit_date = recruitment.getRecruit_date();
        this.place = recruitment.getPlace();
        this.partnerGender = recruitment.getPartnerGender();
        this.partnerAge = recruitment.getPartnerAge();
        this.routine = recruitment.getRoutine();
        this.duration = recruitment.getDuration();
    }
}
