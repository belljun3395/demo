package com.example.member.controller;

import com.example.member.usecase.*;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/members")
@RequiredArgsConstructor
public class MemberController {
	private final CreatMemberUseCase creatMemberUseCase;
	private final UpdatePasswordUseCase updatePasswordUseCase;
	private final GetRelationsUseCase getRelationsUseCase;
	private final RequestRelationUseCase requestRelationUseCase;
	private final AcceptRelationRequestUseCase acceptRelationRequestUseCase;

	@PostMapping
	public String createMember(
			@RequestParam(value = "name") String name,
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password) {
		return creatMemberUseCase.execute(name, email, password);
	}

	@PutMapping
	public String updatePassword(
			@RequestParam(value = "memberId") Long memberId,
			@RequestParam(value = "password") String password) {
		return updatePasswordUseCase.execute(memberId, password);
	}

	@GetMapping("/relations/{memberId}")
	public String getRelations(@PathVariable("memberId") Long targetMemberId) {
		return getRelationsUseCase.execute(targetMemberId);
	}

	@PostMapping(
			value = "/relations/{memberId}",
			headers = {"command=requestRelation"})
	public String requestRelation(
			@RequestParam(value = "memberId") Long memberId,
			@PathVariable("memberId") Long targetMemberI) {
		return requestRelationUseCase.execute(memberId, targetMemberI);
	}

	@PostMapping(
			value = "/relations/{memberId}",
			headers = {"command=acceptRelation"})
	public String acceptRelationRequest(
			@RequestParam(value = "memberId") Long memberId,
			@PathVariable("memberId") Long targetMemberId) {
		return acceptRelationRequestUseCase.execute(memberId, targetMemberId);
	}
}
