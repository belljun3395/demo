package com.example.member.model;

import com.example.member.model.account.Email;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public class Member {
	private final Long id;
	private final String name;
	private final Email email;
}
