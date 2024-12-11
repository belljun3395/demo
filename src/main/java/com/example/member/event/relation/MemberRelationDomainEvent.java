package com.example.member.event.relation;

public abstract class MemberRelationDomainEvent extends MemberRelationEvent {
	public MemberRelationDomainEvent(String eventType, Long time) {
		super(eventType, time);
	}
}
