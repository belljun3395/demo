package com.example.notification.relay;

import com.example.event.message.Message;
import com.example.event.message.MessagePayload;
import lombok.Getter;

@Getter
public class NotificationMessage extends Message {
	public NotificationMessage(MessagePayload payload) {
		super(payload);
	}
}
