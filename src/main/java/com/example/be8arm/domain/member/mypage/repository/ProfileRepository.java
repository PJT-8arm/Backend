package com.example.be8arm.domain.member.mypage.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.be8arm.domain.member.member.entity.Profile;

public interface ProfileRepository extends JpaRepository<Profile, Long> {
}
