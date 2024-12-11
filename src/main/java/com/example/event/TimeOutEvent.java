package com.example.event;

import java.util.Date;
import lombok.Getter;
import org.springframework.context.ApplicationEventPublisher;

/** 타임아웃 이벤트 */
@Getter
public abstract class TimeOutEvent extends Event implements Runnable {
	/** 이벤트 만료 시간 */
	private final Date expiredTime;

	private final ApplicationEventPublisher publisher;

	/** 이벤트 완료 여부 */
	private Boolean completed = false;

	public TimeOutEvent(
			String eventType, Long time, Date expiredTime, ApplicationEventPublisher publisher) {
		super(eventType, time);
		this.expiredTime = expiredTime;
		this.publisher = publisher;
	}

	/** 이벤트 완료 처리 */
	public void complete() {
		this.completed = true;
	}

	/** 이벤트 만료 여부 */
	public boolean isExpired() {
		return !this.completed && System.currentTimeMillis() > this.expiredTime.getTime();
	}

	public abstract TimeOutExpiredEvent getTimeOutExpiredEvent();

	/** 타임아웃 이벤트 발생시 실행될 로직 */
	@Override
	public void run() {
		publishTimeOutEvent(getTimeOutExpiredEvent());
	}

	/** 타임아웃 발생시 생성할 이벤트 */
	public abstract static class TimeOutExpiredEvent extends Event {
		public TimeOutExpiredEvent(String eventType, Long time) {
			super(eventType, time);
		}
	}

	public <T extends TimeOutExpiredEvent> void publishTimeOutEvent(T expiredEvent) {
		publisher.publishEvent(expiredEvent);
	}
}
