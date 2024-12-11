package com.example.event.message;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

/** 내부 이벤트를 외부로 전달하기 위한 메시지 클래스 */
@Getter
@RequiredArgsConstructor
public abstract class Message {
	/** 메시지 페이로드 */
	private final MessagePayload payload;
}
