package com.example.event.message;

import com.example.event.Event;
import java.util.Optional;

/** 내부 이벤트를 외부 메시지로 변환하는 매퍼 인터페이스 */
public interface EventMessageMapper<T extends Event, R extends Message> {
	/** 이벤트를 메시지로 변환한다. */
	Optional<R> map(T event);

	/** 메시지를 이벤트로 변환한다. */
	Optional<T> to(MessagePayload message);
}
