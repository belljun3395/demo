package com.example.member.event;

import com.example.event.Event;
import java.util.Map;

public abstract class MemberEvent extends Event {
	public MemberEvent(String eventType, Long time) {
		super(eventType, time);
	}

	public abstract Map<String, Object> getData();
}
