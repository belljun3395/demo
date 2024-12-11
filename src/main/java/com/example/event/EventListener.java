package com.example.event;

/** 이벤트를 구독하는 리스너. */
public interface EventListener<T extends Event> {

	/**
	 * 이벤트를 구독한다. 구체적인 이벤트 구독 방법은 구현체에서 정의한다.
	 *
	 * @see org.springframework.context.event.EventListener
	 * @see org.springframework.transaction.event.TransactionalEventListener
	 */
	void onEvent(T event);
}
