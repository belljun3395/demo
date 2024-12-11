package com.example.member.event;

import lombok.Getter;

@Getter
public enum MemberEventType {
	RELATION_REQUEST("RELATION_REQUEST"),
	RELATION_ACCEPT("RELATION_ACCEPT"),
	RELATION_REQUEST_ACCEPTED("RELATION_REQUEST_ACCEPTED"),
	;

	private final String eventType;

	MemberEventType(String eventType) {
		this.eventType = eventType;
	}
}
