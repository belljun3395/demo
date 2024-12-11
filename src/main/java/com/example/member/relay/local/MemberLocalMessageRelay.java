package com.example.member.relay.local;

import com.example.member.relay.MemberMessage;
import com.example.member.relay.MemberMessageRelay;
import com.example.member.relay.MemberMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("local")
@Component
@RequiredArgsConstructor
public class MemberLocalMessageRelay extends MemberMessageRelay {
	private final MemberMessageSender messageSender;

	@Override
	public void publish(MemberMessage message) {
		log.info(
				">>>>> Publish message to application: {}, {}",
				message.getPayload().getEventType(),
				message.getPayload().getEventId());
		messageSender.send(message);
	}
}
