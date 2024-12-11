package com.example.member.relay.redis.pubsub;

import com.example.member.relay.MemberMessage;
import com.example.member.relay.MemberMessageSender;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Profile("redis")
@Component
@RequiredArgsConstructor
public class MemberRedisMessageSender extends MemberMessageSender {
	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void send(MemberMessage message) {
		redisTemplate.convertAndSend(TOPIC, message);
	}
}
