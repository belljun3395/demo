package com.example.event.message;

import com.example.event.Event;

/** 외부 메시지를 내부로 반송하는 메시지 릴레이 인터페이스 */
public interface MessageReverseRelay<T extends Event> {
	/** 외부 메시지 내부 이벤트로 변환하여 발행한다. */
	void publish(T message);
}
