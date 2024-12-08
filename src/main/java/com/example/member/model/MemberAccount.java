package com.example.member.model;

import com.example.member.model.account.Account;
import com.example.member.model.account.Password;
import lombok.Getter;

@Getter
public class MemberAccount {
	private final Long id;
	private final Account account;

	public MemberAccount(Long id, Account account) {
		this.id = id;
		this.account = account;
	}

	public MemberAccount updateAccount(String password) {
		return new MemberAccount(id, new Account(account.getEmail(), new Password(password)));
	}
}
