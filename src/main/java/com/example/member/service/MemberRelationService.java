package com.example.member.service;

import com.example.member.model.Member;
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
		List<MemberRelation> toMemberBlockedList =
				memberRepository.findAllToRelations(toMember.getId(), MemberRelationType.BLOCKED);
		boolean isFromMemberBlocked =
				toMemberBlockedList.stream()
						.anyMatch(member -> member.getRelation().getToMemberId().equals(fromMember.getId()));
		if (isFromMemberBlocked) {
			throw new IllegalArgumentException("Blocked member");
		}

		return memberRepository.saveRelation(
				fromMember.getId(), toMember.getId(), MemberRelationType.REQUEST);
	}

	public MemberRelation acceptRequestRelation(MemberRelation requestedMemberRelation) {
		requestedMemberRelation.accept();
		memberRepository.updateRelation(requestedMemberRelation);
		return requestedMemberRelation;
	}
}
