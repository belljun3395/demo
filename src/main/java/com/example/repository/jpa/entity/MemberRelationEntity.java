package com.example.repository.jpa.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
public class MemberRelationEntity {
	@Id private Long id;

	@Column(nullable = false, name = "from_member_id")
	private Long fromMemberId;

	@Column(nullable = false, name = "to_member_id")
	private Long toMemberId;

	@Column(nullable = false, name = "relation_type_id")
	private Long relationTypeId;

	public MemberRelationEntity(Long fromMemberId, Long toMemberId, Long relationTypeId) {
		this.fromMemberId = fromMemberId;
		this.toMemberId = toMemberId;
		this.relationTypeId = relationTypeId;
	}
}
