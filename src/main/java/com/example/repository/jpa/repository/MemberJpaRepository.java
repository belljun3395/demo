package com.example.repository.jpa.repository;

import com.example.repository.jpa.entity.MemberEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberJpaRepository extends JpaRepository<MemberEntity, Long> {
	Boolean existsByEmail(String email);
}
