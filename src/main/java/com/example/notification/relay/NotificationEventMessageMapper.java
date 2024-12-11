package com.example.notification.relay;

import com.example.event.EventUtils;
import com.example.event.message.EventMessageMapper;
import com.example.event.message.MessagePayload;
import com.example.member.event.MemberEventType;
import com.example.notification.event.NotificationEvent;
import com.example.notification.event.NotificationMemberEventType;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class NotificationEventMessageMapper
		implements EventMessageMapper<NotificationEvent, NotificationMessage> {

	@Override
	public Optional<NotificationMessage> map(NotificationEvent event) {
		boolean outBox = EventUtils.isOutBox(event);
		if (!outBox) {
			return Optional.empty();
		}

		return Optional.of(
				new NotificationMessage(
						new MessagePayload(
								event.getEventId(), event.getEventType(), event.getTime(), event.getData())));
	}

	@Override
	public Optional<NotificationEvent> to(MessagePayload messagePayload) {
		if (messagePayload.getEventType().equals(MemberEventType.RELATION_REQUEST.name())) {
			return Optional.of(
					new NotificationEvent(
							NotificationMemberEventType.RELATION_REQUEST.getEventType(),
							System.currentTimeMillis()));
		}
		if (messagePayload.getEventType().equals(MemberEventType.RELATION_REQUEST_ACCEPTED.name())) {
			return Optional.of(
					new NotificationEvent(
							NotificationMemberEventType.RELATION_REQUEST_ACCEPTED.getEventType(),
							System.currentTimeMillis()));
		}
		return Optional.empty();
	}
}
