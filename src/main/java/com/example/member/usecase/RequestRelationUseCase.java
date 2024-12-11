package com.example.member.usecase;

import com.example.member.model.Member;
import com.example.member.model.MemberRelation;
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
public class RequestRelationUseCase {
	private final MemberRepository memberRepository;
	private final MemberRelationService memberRelationService;

	private final ApplicationEventPublisher applicationEventPublisher;

	@Transactional
	public String execute(Long memberId, Long toMemberId) {
		Member member =
				memberRepository
						.findById(memberId)
						.orElseThrow(() -> new IllegalArgumentException("From member not found"));
		Member toMember =
				memberRepository
						.findById(toMemberId)
						.orElseThrow(() -> new IllegalArgumentException("To member not found"));

		MemberRelation memberRelation = memberRelationService.requestRelation(member, toMember);
		Relation relation = memberRelation.getRelation();

		Map<String, String> out =
				Map.of(
						"type", relation.getRelationType().getValue(),
						"fromId", memberId.toString(),
						"toId", relation.getFromMemberId().toString(),
						"toName", relation.getToMemberName(),
						"toEmail", relation.getToMemberEmail().getValue());
		return out.toString();
	}
}
