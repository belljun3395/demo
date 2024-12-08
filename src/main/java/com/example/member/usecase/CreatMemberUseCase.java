package com.example.member.usecase;

import com.example.member.model.Member;
import com.example.member.model.account.*;
import com.example.member.repository.MemberRepository;
import java.util.Map;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

@Component
@RequiredArgsConstructor
public class CreatMemberUseCase {
	private final MemberRepository memberRepository;

	@Transactional
	public String execute(String name, String email, String password) {
		Boolean isExistEmail = memberRepository.existsByEmail(email);
		if (isExistEmail) {
			throw new IllegalArgumentException("Email already exists");
		}

		Email memberEmail = new Email(EmailPolicy.DEFAULT, email);
		Password memberPassword = new Password(PasswordPolicy.DEFAULT, password);
		Account account = new Account(memberEmail, memberPassword);
		Member createdMember = memberRepository.save(name, account);

		Map<String, Object> out =
				Map.of(
						"id", createdMember.getId(),
						"name", createdMember.getName(),
						"email", createdMember.getEmail());
		return out.toString();
	}
}
