package com.example.member.model;

import lombok.Getter;

@Getter
public enum MemberRelationType {
	REQUEST(1L, "REQUEST"),
	ACCEPTED(2L, "ACCEPTED"),
	REJECTED(3L, "REJECTED"),
	BLOCKED(4L, "BLOCKED");

	private final Long id;
	private final String value;

	MemberRelationType(Long id, String value) {
		this.id = id;
		this.value = value;
	}

	public static MemberRelationType fromId(Long id) {
		for (MemberRelationType type : MemberRelationType.values()) {
			if (type.getId().equals(id)) {
				return type;
			}
		}
		throw new IllegalArgumentException("Invalid id: " + id);
	}
}
