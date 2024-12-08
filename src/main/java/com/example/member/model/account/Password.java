package com.example.member.model.account;

import lombok.Getter;

@Getter
public class Password {
	private final String value;

	public Password(PasswordPolicy policy, String value) {
		if (!policy.isValid(value)) {
			throw new IllegalArgumentException("Invalid password");
		}
		this.value = value;
	}

	public Password(String value) {
		this(PasswordPolicy.DEFAULT, value);
	}
}
