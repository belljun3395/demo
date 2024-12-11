package com.example.member.relay;

import com.example.event.EventUtils;
import com.example.event.message.EventMessageMapper;
import com.example.event.message.MessagePayload;
import com.example.member.event.MemberEvent;
import com.example.member.event.MemberEventType;
import com.example.member.event.relation.MemberRelationRequestAcceptedEvent;
import java.util.Optional;
import org.springframework.stereotype.Component;

@Component
public class MemberEventMessageMapper implements EventMessageMapper<MemberEvent, MemberMessage> {

	@Override
	public Optional<MemberMessage> map(MemberEvent event) {
		boolean outBox = EventUtils.isOutBox(event);
		if (!outBox) {
			return Optional.empty();
		}

		return Optional.of(
				new MemberMessage(
						new MessagePayload(
								event.getEventId(), event.getEventType(), event.getTime(), event.getData())));
	}

	@Override
	public Optional<MemberEvent> to(MessagePayload messagePayload) {
		if (messagePayload.getEventType().equals(MemberEventType.RELATION_ACCEPT.name())) {
			return Optional.of(
					new MemberRelationRequestAcceptedEvent(
							MemberEventType.RELATION_REQUEST_ACCEPTED.getEventType(),
							System.currentTimeMillis(),
							Long.valueOf((String) messagePayload.getData().get("toMemberId")),
							Long.valueOf((String) messagePayload.getData().get("fromMemberId"))));
		}
		return Optional.empty();
	}
}
