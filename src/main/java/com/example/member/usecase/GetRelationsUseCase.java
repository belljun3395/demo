package com.example.member.usecase;

import com.example.member.model.Member;
import com.example.member.model.MemberRelation;
import com.example.member.model.MemberRelationType;
import com.example.member.model.relation.Relations;
import com.example.member.repository.MemberRepository;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class GetRelationsUseCase {
	private final MemberRepository memberRepository;

	@Transactional(readOnly = true)
	public String execute(Long memberId) {
		Member member =
				memberRepository
						.findById(memberId)
						.orElseThrow(() -> new IllegalArgumentException("Member not found"));

		List<MemberRelation> memberRelations = new ArrayList<>();
		for (MemberRelationType type :
				List.of(MemberRelationType.REQUEST, MemberRelationType.ACCEPTED)) {
			List<MemberRelation> allToRelations =
					memberRepository.findAllToRelations(member.getId(), type);
			memberRelations.addAll(allToRelations);
		}
		Relations relations =
				new Relations(memberRelations.stream().map(MemberRelation::getRelation).toList());

		Map<String, Relations> out =
				Map.of(
						"requested", relations.filterByTypes(MemberRelationType.REQUEST),
						"accepted", relations.filterByTypes(MemberRelationType.ACCEPTED));
		return out.toString();
	}
}
