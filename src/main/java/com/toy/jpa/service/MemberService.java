package com.toy.jpa.service;

import com.toy.jpa.MemberExitException;
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

    public Optional<Member> findByEmail(String email) {
        return memberRepository.findByEmail(email);
    }

    public Optional<Member> findById(Long id) {
        return memberRepository.findById(id);
    }

    @Transactional
    public void updateMember(Member member) {
        Optional<Member> findMember = memberRepository.findById(member.getId());
        findMember.get().changeMember(member);
    }

    @Transactional
    public void exitMember(Member member) throws MemberExitException {
        member.changeStatus();
    }
}

