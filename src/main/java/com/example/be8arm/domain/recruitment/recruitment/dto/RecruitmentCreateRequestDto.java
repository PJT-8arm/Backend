package com.example.be8arm.domain.recruitment.recruitment.dto;

import lombok.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
public class RecruitmentCreateRequestDto {

    private Long id;

    private String title;

    private String content;

    private LocalDateTime recruit_date;

    private String place;

    private String partnerGender;

    private Integer partnerAge;

    private String routine;

    private LocalTime duration;
}
