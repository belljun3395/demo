package com.example.member.model.account;

public class PasswordPolicy {
	public static PasswordPolicy DEFAULT = new PasswordPolicy(8);

	private final int minLength;

	public PasswordPolicy(int minLength) {
		this.minLength = minLength;
	}

	public boolean isValid(String password) {
		return password.length() >= minLength;
	}
}
