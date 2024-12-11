package com.example.member.relay.redis.pubsub;

import com.example.member.relay.MemberMessage;
import com.example.member.relay.MemberMessageRelay;
import com.example.member.relay.MemberMessageSender;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("redis")
@Component
@RequiredArgsConstructor
public class MemberRedisMessageRelay extends MemberMessageRelay {
	private final MemberMessageSender messageSender;

	@Override
	public void publish(MemberMessage message) {
		log.info(
				">>>>> Publish message to Redis: {}, {}",
				message.getPayload().getEventType(),
				message.getPayload().getEventId());
		messageSender.send(message);
	}
}
