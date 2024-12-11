package com.example.member.event.relation;

public abstract class MemberRelationExternalEvent extends MemberRelationEvent {
	public MemberRelationExternalEvent(String eventType, Long time) {
		super(eventType, time);
	}
}
