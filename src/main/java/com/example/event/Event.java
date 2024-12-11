package com.example.event;

import java.util.Map;
import lombok.Getter;

/** 도메인의 변화를 추적하기 위한 이벤트 객체 */
@Getter
public abstract class Event {
	/** 이벤트 식별자 */
	private final String eventId;
	/**
	 * 이벤트 타입
	 *
	 * <p>도메인의 변화를 설명하 수 있는 타입
	 */
	private final String eventType;
	/** 이벤트 발생 시간 */
	private final Long time;

	public Event(String eventType, Long time) {
		this.eventId = EventUtils.generateEventId();
		this.eventType = eventType;
		this.time = time;
	}

	/**
	 * 이벤트 데이터
	 *
	 * <p>도메인의 변화를 설명하는 데이터
	 */
	public abstract Map<String, Object> getData();
}
