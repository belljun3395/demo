package com.example.member.model.account;

import lombok.Getter;

@Getter
public class Email {
	private final String value;

	public Email(EmailPolicy policy, String value) {
		if (!policy.isValid(value)) {
			throw new IllegalArgumentException("Invalid email address");
		}
		this.value = value;
	}

	public Email(String value) {
		this(EmailPolicy.DEFAULT, value);
	}

	public boolean isValid(String value) {
		return value.contains("@");
	}
}
