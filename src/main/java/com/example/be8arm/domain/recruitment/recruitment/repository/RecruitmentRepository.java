package com.example.be8arm.domain.recruitment.recruitment.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.recruitment.recruitment.entity.Recruitment;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
	List<Recruitment> findAllByMember(Member member);
}
