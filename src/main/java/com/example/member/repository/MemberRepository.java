package com.example.member.repository;

import com.example.member.model.Member;
import com.example.member.model.MemberAccount;
import com.example.member.model.MemberRelation;
import com.example.member.model.MemberRelationType;
import com.example.member.model.account.Account;
import java.util.List;
import java.util.Optional;

public interface MemberRepository {
	Member save(String name, Account account);

	MemberRelation saveRelation(Long fromMemberId, Long toMemberId, MemberRelationType type);

	Member updateAccount(MemberAccount modifiedMemberAccount);

	MemberRelation updateRelation(MemberRelation memberRelation);

	Optional<Member> findById(Long id);

	Optional<MemberRelation> findRelationById(
			Long fromMemberId, Long toMemberId, MemberRelationType type);

	Optional<MemberAccount> findAccountById(Long id);

	List<MemberRelation> findAllToRelations(Long fromMemberId, MemberRelationType type);

	Boolean existsByEmail(String email);
}
