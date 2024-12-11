package com.example.notification.event;

import com.example.event.Event;
import java.util.Map;

public class NotificationEvent extends Event {
	public NotificationEvent(String eventType, Long time) {
		super(eventType, time);
	}

	public Map<String, Object> getData() {
		return Map.of("eventId", getEventId());
	}
	;
}
