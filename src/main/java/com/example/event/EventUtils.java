package com.example.event;

import java.util.UUID;

/** 이벤트 클래스를 위한 유틸리티 클래스 */
public class EventUtils {

	/** 이벤트 식별자 생성 */
	public static String generateEventId() {
		return UUID.randomUUID().toString();
	}

	/** 이벤트가 아웃박스인지 확인 */
	public static boolean isOutBox(Event event) {
		EventDetails details = event.getClass().getAnnotation(EventDetails.class);
		return details.outbox();
	}
}
