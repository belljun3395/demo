package com.example.member.service;

import com.example.member.model.Member;
import com.example.member.model.MemberBlockRelations;
import com.example.member.model.MemberRelation;
import com.example.member.model.MemberRelationType;
import com.example.member.repository.MemberRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MemberRelationService {
	private final MemberRepository memberRepository;

	public MemberRelation requestRelation(Member fromMember, Member toMember) {
		MemberBlockRelations memberRelations = getMemberBlockRelations(toMember);
		boolean isBlockedMember = memberRelations.isBlockedMember(fromMember.getId());
		if (isBlockedMember) {
			throw new IllegalArgumentException("Blocked member");
		}

		return memberRepository.saveRelation(
				fromMember.getId(), toMember.getId(), MemberRelationType.REQUEST);
	}

	private MemberBlockRelations getMemberBlockRelations(Member member) {
		List<MemberRelation> toRelationMembers =
				memberRepository.findAllToRelations(member.getId(), MemberRelationType.BLOCKED);
		return new MemberBlockRelations(member.getId(), toRelationMembers);
	}

	public MemberRelation acceptRequestRelation(MemberRelation requestedMemberRelation) {
		MemberRelation acceptedRequestRelation = requestedMemberRelation.accept();

		memberRepository.saveRelation(
				acceptedRequestRelation.getRelation().getToMemberId(),
				acceptedRequestRelation.getRelation().getFromMemberId(),
				MemberRelationType.ACCEPTED);
		memberRepository.updateRelation(acceptedRequestRelation);

		return acceptedRequestRelation;
	}
}
