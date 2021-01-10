package com.toy.jpa.service;

import com.toy.jpa.domain.status.ExitStatus;
import com.toy.jpa.dto.FindMemberResponseDto;
import com.toy.jpa.dto.MemberJoinRequestDto;
import com.toy.jpa.dto.UpdateMemberRequestDto;
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
    public Long join(MemberJoinRequestDto dto) {
        Member member = new Member();
        member.joinMember(dto);
        Member saved = memberRepository.save(member);
        return saved.getId();
    }

    public FindMemberResponseDto findByEmail(String email) {
        Optional<Member> findMember = memberRepository.findByEmailAndStatus(email, ExitStatus.JOIN);
        if (findMember.isPresent()) {
            return convertFindMemberDto(findMember);
        } else {
            throw new IllegalStateException("존재하지 않는 회원입니다.");
        }
    }

    public Member findById(Long id) {
        return memberRepository.findById(id).orElseThrow();
    }

    @Transactional
    public void updateMember(Long memberId, UpdateMemberRequestDto dto) {
        Optional<Member> findMember = memberRepository.findById(memberId);
        findMember.get().changeMember(dto);
    }

    @Transactional
    public void exitMember(Long id) {
        Optional<Member> findMember = memberRepository.findById(id);
        findMember.get().changeStatus();
    }

    private FindMemberResponseDto convertFindMemberDto(Optional<Member> member) {
        return FindMemberResponseDto.builder()
                .id(member.get().getId())
                .name(member.get().getName())
                .email(member.get().getEmail())
                .city(member.get().getAddress().getCity())
                .street(member.get().getAddress().getStreet())
                .zipcode(member.get().getAddress().getZipcode())
                .build();
    }
}

