package com.example.member.repository;

import com.example.member.model.Member;
import com.example.member.model.MemberAccount;
import com.example.member.model.MemberRelation;
import com.example.member.model.MemberRelationType;
import com.example.member.model.account.Account;
import com.example.member.model.account.Email;
import com.example.member.model.account.Password;
import com.example.member.model.relation.Relation;
import com.example.repository.jpa.entity.MemberEntity;
import com.example.repository.jpa.entity.MemberRelationEntity;
import com.example.repository.jpa.repository.MemberJpaRepository;
import com.example.repository.jpa.repository.MemberRelationJpaRepository;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class MemberRepositoryImpl implements MemberRepository {
	private final MemberJpaRepository memberJpaRepository;
	private final MemberRelationJpaRepository memberRelationJpaRepository;

	@Override
	public Member save(String name, Account account) {
		MemberEntity source =
				new MemberEntity(name, account.getEmail().getValue(), account.getPassword().getValue());
		MemberEntity entity = memberJpaRepository.save(source);
		return new Member(entity.getId(), entity.getName(), new Email(entity.getEmail()));
	}

	@Override
	public MemberRelation saveRelation(Long fromMemberId, Long toMemberId, MemberRelationType type) {
		MemberRelationEntity relationSource =
				new MemberRelationEntity(fromMemberId, toMemberId, type.getId());
		memberRelationJpaRepository.save(relationSource);

		MemberEntity toMember =
				memberJpaRepository
						.findById(toMemberId)
						.orElseThrow(() -> new IllegalArgumentException("Member not found"));

		Relation relation =
				new Relation(
						type,
						fromMemberId,
						toMember.getId(),
						toMember.getName(),
						new Email(toMember.getEmail()));

		if (type.equals(MemberRelationType.REQUEST)) {
			return MemberRelation.requestRelation(fromMemberId, relation);
		}
		return new MemberRelation(fromMemberId, relation);
	}

	@Override
	public Member updateAccount(MemberAccount modifiedMemberAccount) {
		MemberEntity origin =
				memberJpaRepository
						.findById(modifiedMemberAccount.getId())
						.orElseThrow(() -> new IllegalArgumentException("Member not found"));

		MemberEntity source =
				new MemberEntity(
						origin.getId(),
						origin.getName(),
						modifiedMemberAccount.getAccount().getEmail().getValue(),
						modifiedMemberAccount.getAccount().getPassword().getValue());
		MemberEntity entity = memberJpaRepository.save(source);

		return new Member(entity.getId(), entity.getName(), new Email(entity.getEmail()));
	}

	@Override
	public MemberRelation updateRelation(MemberRelation modifiedMemberRelation) {
		MemberRelationEntity origin =
				memberRelationJpaRepository
						.findTopByFromMemberIdAndToMemberId(
								modifiedMemberRelation.getRelation().getFromMemberId(),
								modifiedMemberRelation.getRelation().getToMemberId())
						.orElseThrow(() -> new IllegalArgumentException("Relation not found"));

		MemberRelationEntity source =
				new MemberRelationEntity(
						origin.getId(),
						modifiedMemberRelation.getRelation().getFromMemberId(),
						modifiedMemberRelation.getRelation().getToMemberId(),
						modifiedMemberRelation.getRelation().getRelationType().getId());
		MemberRelationEntity entity = memberRelationJpaRepository.save(source);

		MemberEntity toMember =
				memberJpaRepository
						.findById(entity.getToMemberId())
						.orElseThrow(() -> new IllegalArgumentException("Member not found"));

		return new MemberRelation(
				entity.getFromMemberId(),
				new Relation(
						entity.getRelationTypeId(),
						entity.getFromMemberId(),
						toMember.getId(),
						toMember.getName(),
						new Email(toMember.getEmail())));
	}

	@Override
	public Optional<Member> findById(Long id) {
		return memberJpaRepository
				.findById(id)
				.map(entity -> new Member(entity.getId(), entity.getName(), new Email(entity.getEmail())));
	}

	@Override
	public Optional<MemberRelation> findRelationById(
			Long fromMemberId, Long toMemberId, MemberRelationType type) {
		return memberRelationJpaRepository
				.findByFromMemberIdAndToMemberIdAndRelationTypeId(fromMemberId, toMemberId, type.getId())
				.map(
						entity -> {
							MemberEntity toMember =
									memberJpaRepository
											.findById(entity.getToMemberId())
											.orElseThrow(() -> new IllegalArgumentException("Member not found"));
							return new MemberRelation(
									fromMemberId,
									new Relation(
											type,
											entity.getFromMemberId(),
											toMember.getId(),
											toMember.getName(),
											new Email(toMember.getEmail())));
						});
	}

	@Override
	public Optional<MemberAccount> findAccountById(Long id) {
		return memberJpaRepository
				.findById(id)
				.map(
						entity ->
								new MemberAccount(
										entity.getId(),
										new Account(new Email(entity.getEmail()), new Password(entity.getPassword()))));
	}

	@Override
	public List<MemberRelation> findAllToRelations(Long fromMemberId, MemberRelationType type) {
		return memberRelationJpaRepository
				.findAllByFromMemberIdAndRelationTypeId(fromMemberId, type.getId())
				.stream()
				.map(
						entity -> {
							MemberEntity toMember =
									memberJpaRepository.findById(entity.getToMemberId()).orElse(null);
							if (toMember == null) {
								return null;
							}
							return new MemberRelation(
									fromMemberId,
									new Relation(
											entity.getRelationTypeId(),
											entity.getFromMemberId(),
											toMember.getId(),
											toMember.getName(),
											new Email(toMember.getEmail())));
						})
				.filter(Objects::nonNull)
				.toList();
	}

	@Override
	public Boolean existsByEmail(String email) {
		return memberJpaRepository.existsByEmail(email);
	}
}
