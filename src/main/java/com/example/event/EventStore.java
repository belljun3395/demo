package com.example.event;

/** 발행된 이벤트를 저장하는 저장소 */
public interface EventStore<T extends Event> {

	/** 이벤트를 저장한다 */
	T save(T event);
}
