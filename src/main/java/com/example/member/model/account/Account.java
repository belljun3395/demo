package com.example.member.model.account;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Account {
	private final Email email;
	private final Password password;
}
