package com.example.notification.relay.local;

import com.example.notification.event.NotificationEvent;
import com.example.notification.relay.NotificationEventMessageMapper;
import com.example.notification.relay.NotificationMemberMessageReverseRelay;
import com.example.notification.relay.NotificationMessage;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("local")
@Component
@RequiredArgsConstructor
public class NotificationMemberLocalMessageReverseRelay
		extends NotificationMemberMessageReverseRelay {
	private final NotificationEventMessageMapper messageMapper;
	private final ApplicationEventPublisher applicationEventPublisher;

	@EventListener
	public void onApplicationEvent(NotificationMessage event) {
		log.info(
				"<<<<< Received message from application: {}, {}",
				event.getPayload().getEventType(),
				event.getPayload().getEventId());
		messageMapper.to(event.getPayload()).ifPresent(this::publish);
	}

	@Override
	public void publish(NotificationEvent message) {
		log.info(">>>>> Publish message to application: {}", message.getEventId());
		applicationEventPublisher.publishEvent(message);
	}
}
