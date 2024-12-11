package com.example.member.relay;

import com.example.event.message.MessageSender;

public abstract class MemberMessageSender implements MessageSender<MemberMessage> {
	protected static final String TOPIC = "member";
}
