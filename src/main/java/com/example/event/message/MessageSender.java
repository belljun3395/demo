package com.example.event.message;

/** 메시지 발송 인터페이스 */
public interface MessageSender<T extends Message> {
	/** 메시지를 발송한다. */
	void send(T message);
}
