package com.example.notification.relay.redis;

import com.example.config.RedisConfig;
import com.example.notification.relay.redis.pubsub.NotificationMemberRedisMessageReverseRelay;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Profile;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.ChannelTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

@Profile("redis")
@Import(RedisConfig.class)
@Configuration
@RequiredArgsConstructor
public class NotificationRedisMessageConfig {
	private final ChannelTopic memberTopic;

	@Bean(name = "notificationFromMemberMessageReverseRelayListenerAdapter")
	public MessageListenerAdapter messageReverseRelayListenerAdapter(
			NotificationMemberRedisMessageReverseRelay messageReverseRelay) {
		return new MessageListenerAdapter(messageReverseRelay, "onMessage");
	}

	@Bean(name = "notificationFromMemberMessageReverseRelayListenerContainer")
	public RedisMessageListenerContainer messageReverseRelayListenerContainer(
			RedisConnectionFactory redisConnectionFactory,
			@Qualifier("notificationFromMemberMessageReverseRelayListenerAdapter")
					MessageListenerAdapter messageReverseRelayListenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory);
		container.addMessageListener(messageReverseRelayListenerAdapter, memberTopic);
		return container;
	}
}
