package com.toy.jpa.repository;

import com.toy.jpa.domain.status.ExitStatus;
import com.toy.jpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByEmailAndStatus(String email, ExitStatus status);

    Optional<Member> findByNameAndStatus(String name, ExitStatus status);
}
