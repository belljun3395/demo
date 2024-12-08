package com.example.repository.jpa.repository;

import com.example.repository.jpa.entity.MemberRelationEntity;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRelationJpaRepository extends JpaRepository<MemberRelationEntity, Long> {

	Optional<MemberRelationEntity> findByFromMemberIdAndToMemberIdAndRelationTypeId(
			Long fromMemberId, Long toMemberId, Long relationTypeId);

	Optional<MemberRelationEntity> findTopByFromMemberIdAndToMemberId(
			Long fromMemberId, Long toMemberId);

	List<MemberRelationEntity> findAllByFromMemberIdAndRelationTypeId(
			Long fromMemberId, Long relationTypeId);
}
