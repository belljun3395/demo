package com.example.member.event.relation;

import com.example.event.EventHandler;
import com.example.member.relay.MemberEventMessageMapper;
import com.example.member.relay.MemberMessageRelay;
import java.util.List;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MemberRelationEventListener
		implements com.example.event.EventListener<MemberRelationEvent> {
	private final List<EventHandler<MemberRelationRequestAcceptedEvent>>
			relationRequestAcceptedEventEventHandlers;

	private final MemberMessageRelay messageRelay;
	private final MemberEventMessageMapper eventMessageMapper;

	@Override
	@EventListener
	public void onEvent(MemberRelationEvent event) {
		log.info("<<<<< Received event: {}, {}", event.getEventType(), event.getEventId());
		if (event instanceof MemberRelationRequestAcceptedEvent requestAcceptedEvent) {
			relationRequestAcceptedEventEventHandlers.forEach(
					handler -> handler.handle(requestAcceptedEvent));
		}

		eventMessageMapper.map(event).ifPresent(messageRelay::publish);
	}
}
