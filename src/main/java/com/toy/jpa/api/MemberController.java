package com.toy.jpa.api;

import com.toy.jpa.config.security.JwtTokenProvider;
import com.toy.jpa.domain.Member;
import com.toy.jpa.domain.status.ExitStatus;
import com.toy.jpa.dto.FindMemberResponseDto;
import com.toy.jpa.dto.MemberJoinRequestDto;
import com.toy.jpa.dto.MemberLoginDto;
import com.toy.jpa.repository.MemberRepository;
import com.toy.jpa.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RequiredArgsConstructor
@RestController
public class MemberController {

    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final MemberService memberService;
    private final MemberRepository memberRepository;

    @PostMapping("/join")
    public Long join(@RequestBody MemberJoinRequestDto dto) {
        return memberService.join(dto);
    }

    @PostMapping("/login")
    public String login(@RequestBody MemberLoginDto dto) {
        Member findMember = memberRepository.findByEmailAndStatus(dto.getEmail(), ExitStatus.JOIN)
                .orElseThrow(() -> new IllegalArgumentException("가입되지 않은 email"));

        if (!passwordEncoder.matches(dto.getPassword(), findMember.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }
        return jwtTokenProvider.createToken(findMember.getName(), findMember.getRoles());
    }
}