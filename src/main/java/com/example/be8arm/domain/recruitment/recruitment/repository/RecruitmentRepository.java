package com.example.be8arm.domain.recruitment.recruitment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.example.be8arm.domain.member.member.entity.Member;
import com.example.be8arm.domain.recruitment.recruitment.entity.Recruitment;

public interface RecruitmentRepository extends JpaRepository<Recruitment, Long> {
	Page<Recruitment> findAllByMember(Member member, Pageable pageable);
}
