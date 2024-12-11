package com.example.event;

import java.lang.annotation.*;

/**
 * 이벤트 객체에 대한 추가 상세 정보를 제공하는 어노테이션
 *
 * <p>이벤트 비즈니스와는 관련이 없는 이벤트 객체에 대한 추가 정보를 제공한다.
 *
 * @see Event
 */
@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface EventDetails {

	/** 해당 이벤트가 외부로 전송되어야 하는지 여부 */
	boolean outbox() default false;
}
