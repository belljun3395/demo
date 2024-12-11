package com.example.member.event.relation.handler;

import com.example.event.EventHandler;
import com.example.member.event.relation.MemberRelationRequestAcceptedEvent;
import com.example.member.model.MemberRelationType;
import com.example.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class MemberRelationRequestAcceptedEventHandler
		implements EventHandler<MemberRelationRequestAcceptedEvent> {
	private final MemberRepository memberRepository;

	@Transactional
	public void handle(MemberRelationRequestAcceptedEvent event) {
		memberRepository.saveRelation(
				event.getFromMemberId(), event.getToMemberId(), MemberRelationType.ACCEPTED);
	}
}
