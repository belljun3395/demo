package com.example.notification.event;

import lombok.Getter;

@Getter
public enum NotificationMemberEventType {
	RELATION_REQUEST("NOTIFICATION_RELATION_REQUEST"),
	RELATION_ACCEPT("NOTIFICATION_RELATION_ACCEPT"),
	RELATION_REQUEST_ACCEPTED("NOTIFICATION_RELATION_REQUEST_ACCEPTED"),
	;

	private final String eventType;

	NotificationMemberEventType(String eventType) {
		this.eventType = eventType;
	}
}
