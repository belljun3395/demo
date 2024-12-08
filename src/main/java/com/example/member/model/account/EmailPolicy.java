package com.example.member.model.account;

public class EmailPolicy {
	public static EmailPolicy DEFAULT = new EmailPolicy();

	private EmailPolicy() {}

	private String validDomainList;

	public EmailPolicy(String validDomainList) {
		this.validDomainList = validDomainList;
	}

	public boolean isValid(String value) {
		if (!isValidFormat(value)) return false;

		String domain = value.split("@")[1];
		if (!isValidDomain(domain)) return false;
		return true;
	}

	private boolean isValidDomain(String domain) {
		if (validDomainList == null) return true;
		return !validDomainList.contains(domain);
	}

	private boolean isValidFormat(String value) {
		return value.contains("@");
	}
}
