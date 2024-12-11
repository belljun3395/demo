package com.example.event.message;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.Map;
import lombok.Getter;

/** 메시지에 포함할 정보를 정의하는 페이로드 */
@Getter
public class MessagePayload {
	private final String eventId;
	private final String eventType;
	private final Long eventOccurredTime;
	private final Map<String, Object> data;

	@JsonCreator
	public MessagePayload(
			@JsonProperty("eventId") String eventId,
			@JsonProperty("eventType") String eventType,
			@JsonProperty("eventOccurredTime") Long eventOccurredTime,
			@JsonProperty("data") Map<String, Object> data) {
		this.eventId = eventId;
		this.eventType = eventType;
		this.eventOccurredTime = eventOccurredTime;
		this.data = data;
	}
}
