package com.example.member.relay;

import com.example.event.message.Message;
import com.example.event.message.MessagePayload;
import lombok.Getter;

@Getter
public class MemberMessage extends Message {
	public MemberMessage(MessagePayload payload) {
		super(payload);
	}
}
