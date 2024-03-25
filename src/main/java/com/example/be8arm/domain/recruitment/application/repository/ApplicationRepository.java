package com.example.be8arm.domain.recruitment.application.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.be8arm.domain.recruitment.application.entity.Application;

public interface ApplicationRepository extends JpaRepository<Application, Long> {
}
