package com.example.member.model;

import com.example.member.event.MemberEventType;
import com.example.member.event.relation.MemberRelationAcceptEvent;
import com.example.member.event.relation.MemberRelationEvent;
import com.example.member.event.relation.MemberRelationRequestEvent;
import com.example.member.model.account.Email;
import com.example.member.model.relation.Relation;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import lombok.Getter;

@Getter
public class MemberRelation {
	private final List<MemberRelationEvent> events;
	private final Long id;

	private Relation relation;

	public MemberRelation(Long id, Relation relation) {
		this.id = id;
		if (!isValidRelation(id, relation)) {
			throw new IllegalArgumentException("Invalid relation");
		}
		this.relation = relation;
		this.events = new ArrayList<>();
	}

	public MemberRelation(Long id, Relation relation, List<MemberRelationEvent> events) {
		this.id = id;
		if (!isValidRelation(id, relation)) {
			throw new IllegalArgumentException("Invalid relation");
		}
		this.relation = relation;
		this.events = events;
	}

	public static MemberRelation requestRelation(Long id, Relation relation) {
		List<MemberRelationEvent> events = new ArrayList<>();
		events.add(
				new MemberRelationRequestEvent(
						MemberEventType.RELATION_REQUEST.getEventType(),
						System.currentTimeMillis(),
						relation.getFromMemberId(),
						relation.getToMemberId()));
		return new MemberRelation(id, relation, events);
	}

	private Boolean isValidRelation(Long id, Relation relation) {
		return Objects.equals(id, relation.getFromMemberId());
	}

	public void accept() {
		events.add(
				new MemberRelationAcceptEvent(
						MemberEventType.RELATION_ACCEPT.getEventType(),
						System.currentTimeMillis(),
						relation.getFromMemberId(),
						relation.getToMemberId()));
		this.relation =
				new Relation(
						MemberRelationType.ACCEPTED.getId(),
						relation.getFromMemberId(),
						relation.getToMemberId(),
						relation.getToMemberName(),
						new Email(relation.getToMemberEmail().getValue()));
	}
}
