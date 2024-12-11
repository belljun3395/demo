package com.example.member.relay.redis;

import com.example.config.RedisConfig;
import com.example.member.relay.redis.pubsub.MemberRedisMessageReverseRelay;
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
public class MemberRedisMessageConfig {

	@Bean
	public ChannelTopic memberTopic() {
		return new ChannelTopic("member");
	}

	@Bean(name = "memberMessageReverseRelayListenerAdapter")
	public MessageListenerAdapter messageReverseRelayListenerAdapter(
			MemberRedisMessageReverseRelay messageReverseRelay) {
		return new MessageListenerAdapter(messageReverseRelay, "onMessage");
	}

	@Bean(name = "memberMessageReverseRelayListenerContainer")
	public RedisMessageListenerContainer messageReverseRelayListenerContainer(
			RedisConnectionFactory redisConnectionFactory,
			@Qualifier("memberMessageReverseRelayListenerAdapter")
					MessageListenerAdapter messageReverseRelayListenerAdapter) {
		RedisMessageListenerContainer container = new RedisMessageListenerContainer();
		container.setConnectionFactory(redisConnectionFactory);
		container.addMessageListener(messageReverseRelayListenerAdapter, memberTopic());
		return container;
	}
}
