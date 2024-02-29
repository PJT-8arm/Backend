package com.example.be8arm.domain.recruitment.recruitment.entity;

import static lombok.AccessLevel.*;

import java.time.LocalDateTime;
import java.time.LocalTime;

import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.data.annotation.CreatedDate;

import com.example.be8arm.domain.member.member.entity.Member;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

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

	private LocalTime duration;
}
