package com.example.notification.event;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class NotificationEventListener
		implements com.example.event.EventListener<NotificationEvent> {
	@Override
	@EventListener
	public void onEvent(NotificationEvent event) {
		log.info("<<<<< Received event: {}, {}", event.getEventType(), event.getEventId());
	}
}
