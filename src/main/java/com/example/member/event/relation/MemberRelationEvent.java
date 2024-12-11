package com.example.member.event.relation;

import com.example.member.event.MemberEvent;
import java.util.Map;
import lombok.Getter;

@Getter
public abstract class MemberRelationEvent extends MemberEvent {
	public MemberRelationEvent(String eventType, Long time) {
		super(eventType, time);
	}

	public abstract Map<String, Object> getData();
}
