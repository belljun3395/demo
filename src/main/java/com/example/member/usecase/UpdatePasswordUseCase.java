package com.example.member.usecase;

import com.example.member.model.Member;
import com.example.member.model.MemberAccount;
import com.example.member.repository.MemberRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class UpdatePasswordUseCase {
	private final MemberRepository memberRepository;

	@Transactional
	public String execute(Long memberId, String newPassword) {
		Member member =
				memberRepository
						.findById(memberId)
						.orElseThrow(() -> new IllegalArgumentException("Member not found"));

		MemberAccount memberAccount =
				memberRepository
						.findAccountById(member.getId())
						.orElseThrow(() -> new IllegalArgumentException("Member Account not found"));
		MemberAccount updatedMemberAccount = memberAccount.updateAccount(newPassword);
		Member updatedMember = memberRepository.updateAccount(updatedMemberAccount);

		Map<String, String> out =
				Map.of(
						"id", updatedMember.getId().toString(),
						"name", updatedMember.getName(),
						"email", updatedMember.getEmail().getValue());
		return out.toString();
	}
}
