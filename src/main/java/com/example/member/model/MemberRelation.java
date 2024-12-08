package com.example.member.model;

import com.example.member.model.relation.Relation;
import java.util.Objects;
import lombok.Getter;

@Getter
public class MemberRelation {
	private final Long id;
	private final Relation relation;

	public MemberRelation(Long id, Relation relation) {
		this.id = id;
		if (!isValidRelation(id, relation)) {
			throw new IllegalArgumentException("Invalid relation");
		}
		this.relation = relation;
	}

	private Boolean isValidRelation(Long id, Relation relation) {
		return Objects.equals(id, relation.getFromMemberId());
	}

	public MemberRelation accept() {
		return new MemberRelation(
				id,
				new Relation(
						MemberRelationType.ACCEPTED,
						relation.getFromMemberId(),
						relation.getToMemberId(),
						relation.getToMemberName(),
						relation.getToMemberEmail()));
	}
}
