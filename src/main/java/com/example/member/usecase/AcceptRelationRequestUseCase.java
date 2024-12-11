package com.example.member.usecase;

import com.example.member.model.Member;
import com.example.member.model.MemberRelation;
import com.example.member.model.MemberRelationType;
import com.example.member.model.relation.Relation;
import com.example.member.repository.MemberRepository;
import com.example.member.service.MemberRelationService;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class AcceptRelationRequestUseCase {
	private final MemberRepository memberRepository;
	private final MemberRelationService memberRelationService;

	private final ApplicationEventPublisher applicationEventPublisher;

	@Transactional
	public String execute(Long memberId, Long requestMemberId) {
		Member member =
				memberRepository
						.findById(memberId)
						.orElseThrow(() -> new IllegalArgumentException("Member not found"));

		MemberRelation requestedMemberRelation =
				memberRepository
						.findRelationById(requestMemberId, member.getId(), MemberRelationType.REQUEST)
						.orElseThrow(() -> new IllegalArgumentException("Relation not found"));
		MemberRelation acceptedRequestRelation =
				memberRelationService.acceptRequestRelation(requestedMemberRelation);
		Relation relation = acceptedRequestRelation.getRelation();

		Map<String, String> out =
				Map.of(
						"type", relation.getRelationType().getValue(),
						"fromId", relation.getFromMemberId().toString(),
						"toId", relation.getToMemberId().toString(),
						"toName", relation.getToMemberName(),
						"toEmail", relation.getToMemberEmail().getValue());
		return out.toString();
	}
}
