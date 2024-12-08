package com.example.member.model.relation;

import com.example.member.model.MemberRelationType;
import com.example.member.model.account.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Relation {
	private final MemberRelationType relationType;
	private final Long fromMemberId;
	private final Long toMemberId;
	private final String toMemberName;
	private final Email toMemberEmail;

	public Relation(
			Long relationTypeId,
			Long fromMemberId,
			Long toMemberId,
			String toMemberName,
			Email toMemberEmail) {
		this.relationType = MemberRelationType.fromId(relationTypeId);
		this.fromMemberId = fromMemberId;
		this.toMemberId = toMemberId;
		this.toMemberName = toMemberName;
		this.toMemberEmail = toMemberEmail;
	}
}
