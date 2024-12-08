package com.example.member.model.relation;

import com.example.member.model.MemberRelationType;
import java.util.List;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

@ToString
@Getter
@RequiredArgsConstructor
public class Relations {
	private final List<Relation> relations;

	public Relations filterByTypes(MemberRelationType type) {
		return new Relations(
				relations.stream().filter(relation -> relation.getRelationType() == type).toList());
	}
}
