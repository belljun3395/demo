package com.example.event.message;

/** 메시지 릴레이 인터페이스 */
public interface MessageRelay<T extends Message> {
	/** 내부 이벤트를 외부 메시지로 변환하여 발행한다. */
	void publish(T message);
}
