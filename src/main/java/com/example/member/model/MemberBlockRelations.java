package com.example.member.model;

import java.util.List;
import lombok.Getter;

@Getter
public class MemberBlockRelations {
	private final Long id;
	private final List<MemberRelation> toRelationMembers;

	public MemberBlockRelations(Long id, List<MemberRelation> toRelationMembers) {
		this.id = id;
		if (!isValidMemberRelations(toRelationMembers)) {
			throw new IllegalArgumentException("Invalid member relations");
		}
		this.toRelationMembers = toRelationMembers;
	}

	private Boolean isValidMemberRelations(List<MemberRelation> toRelationMembers) {
		return toRelationMembers.stream()
				.allMatch(
						relation ->
								relation.getRelation().getRelationType().equals(MemberRelationType.BLOCKED));
	}

	public Boolean isBlockedMember(Long memberId) {
		return toRelationMembers.stream()
				.anyMatch(member -> member.getRelation().getToMemberId().equals(memberId));
	}
}
