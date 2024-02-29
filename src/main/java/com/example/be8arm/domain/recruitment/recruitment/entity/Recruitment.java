package com.example.be8arm.domain.recruitment.recruitment.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.sql.Timestamp;
import java.time.LocalDateTime;

import static lombok.AccessLevel.PROTECTED;

@Entity
@Setter
@Getter
@AllArgsConstructor(access = PROTECTED)
@NoArgsConstructor(access = PROTECTED)
@Builder
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @CreatedDate
    private LocalDateTime create_date;

    @UpdateTimestamp
    private LocalDateTime modify_date;

    private String title;

    private String content;

    private LocalDateTime recruit_date;

    private String place;

    private String partnerGender;

    private int partnerAge;

    private String routine;

    private Timestamp duration;
}
