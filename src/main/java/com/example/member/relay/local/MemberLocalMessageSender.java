package com.example.member.relay.local;

import com.example.member.relay.MemberMessage;
import com.example.member.relay.MemberMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Profile("local")
@Component
@RequiredArgsConstructor
public class MemberLocalMessageSender extends MemberMessageSender {
	private final ApplicationEventPublisher applicationEventPublisher;

	@Override
	public void send(MemberMessage message) {
		applicationEventPublisher.publishEvent(new ApplicationEvent(message) {});
	}
}
