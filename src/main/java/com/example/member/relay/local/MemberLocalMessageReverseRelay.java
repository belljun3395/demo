package com.example.member.relay.local;

import com.example.member.event.MemberEvent;
import com.example.member.relay.MemberEventMessageMapper;
import com.example.member.relay.MemberMessage;
import com.example.member.relay.MemberMessageReverseRelay;
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
public class MemberLocalMessageReverseRelay extends MemberMessageReverseRelay {
	private final MemberEventMessageMapper messageMapper;
	private final ApplicationEventPublisher applicationEventPublisher;

	@EventListener
	public void onApplicationEvent(MemberMessage event) {
		log.info(
				"<<<<< Received message from application: {}, {}",
				event.getPayload().getEventType(),
				event.getPayload().getEventId());
		messageMapper.to(event.getPayload()).ifPresent(this::publish);
	}

	@Override
	public void publish(MemberEvent message) {
		log.info(
				">>>>> Publish message to application: {}, {}",
				message.getEventType(),
				message.getEventId());
		applicationEventPublisher.publishEvent(message);
	}
}
