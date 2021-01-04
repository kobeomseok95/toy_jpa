package com.toy.jpa.service;

import com.toy.jpa.dto.FindMemberDto;
import com.toy.jpa.dto.UpdateMemberRequestDto;
import com.toy.jpa.exception.MemberExitException;
import com.toy.jpa.domain.Member;
import com.toy.jpa.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class MemberService {

    private final MemberRepository memberRepository;

    @Transactional
    public void join(Member member) {
        memberRepository.save(member);
    }

    public FindMemberDto findByEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmail(email);
        return convertFindMemberDto(findMember);
    }

    public FindMemberDto findById(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        return convertFindMemberDto(findMember);
    }

    private FindMemberDto convertFindMemberDto(Optional<Member> member) {
        return new FindMemberDto(member.get());
    }

    @Transactional
    public void updateMember(Long memberId, UpdateMemberRequestDto dto) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        findMember.get().changeMember(dto);
    }

    @Transactional
    public void exitMember(Member member) throws MemberExitException {
        member.changeStatus();
    }
}

