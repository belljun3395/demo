package com.example.notification.relay.redis.pubsub;

import com.example.event.message.MessagePayload;
import com.example.notification.event.NotificationEvent;
import com.example.notification.relay.NotificationEventMessageMapper;
import com.example.notification.relay.NotificationMemberMessageReverseRelay;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Component;

@Slf4j
@Profile("redis")
@Component
@RequiredArgsConstructor
public class NotificationMemberRedisMessageReverseRelay
		extends NotificationMemberMessageReverseRelay implements MessageListener {
	private final NotificationEventMessageMapper messageMapper;

	private final ApplicationEventPublisher applicationEventPublisher;
	private final ObjectMapper objectMapper;

	private final RedisTemplate<String, Object> redisTemplate;

	@Override
	public void onMessage(Message message, byte[] pattern) {
		MessagePayload messagePayload = convertToPayload(message);
		log.info(
				"<<<<< Received message from Redis: {}, {}",
				messagePayload.getEventType(),
				messagePayload.getEventId());
		messageMapper.to(messagePayload).ifPresent(this::publish);
	}

	@Override
	public void publish(NotificationEvent message) {
		log.info(
				">>>>> Publish message to application: {}, {}",
				message.getEventType(),
				message.getEventId());
		applicationEventPublisher.publishEvent(message);
	}

	private MessagePayload convertToPayload(Message message) {
		try {
			Object payload =
					objectMapper
							.readValue(
									redisTemplate.getStringSerializer().deserialize(message.getBody()), Map.class)
							.get("payload");
			return objectMapper.readValue(objectMapper.writeValueAsString(payload), MessagePayload.class);
		} catch (JsonProcessingException e) {
			throw new RuntimeException(e);
		}
	}
}
