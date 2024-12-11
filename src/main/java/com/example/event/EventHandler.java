package com.example.event;

/** 이벤트를 처리하는 핸들러 인터페이스 */
public interface EventHandler<T extends Event> {
	void handle(T event);
}
