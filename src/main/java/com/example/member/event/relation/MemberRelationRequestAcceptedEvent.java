package com.example.member.event.relation;

import com.example.event.EventDetails;
import java.util.Map;
import lombok.Getter;

@Getter
@EventDetails(outbox = true)
public class MemberRelationRequestAcceptedEvent extends MemberRelationExternalEvent {
	private final Long fromMemberId;
	private final Long toMemberId;

	public MemberRelationRequestAcceptedEvent(
			String eventType, Long time, Long fromMemberId, Long toMemberId) {
		super(eventType, time);
		this.fromMemberId = fromMemberId;
		this.toMemberId = toMemberId;
	}

	@Override
	public Map<String, Object> getData() {
		return Map.of(
				"fromMemberId", fromMemberId.toString(),
				"toMemberId", toMemberId.toString());
	}
}
