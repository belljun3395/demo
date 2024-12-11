package com.example.event;

/** 타임 아웃 이벤트 재구성기 */
public abstract class TimeOutEventReconstituter {
	/** 처리되지 않은 타임 아웃 이벤트를 재구성한다. */
	abstract void reconstituteTimeOutEvents();
}
