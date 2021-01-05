package com.toy.jpa.repository;

import com.toy.jpa.domain.ExitStatus;
import com.toy.jpa.domain.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long> {


    Optional<Member> findByEmailAndStatus(String email, ExitStatus status);

    Optional<Member> findByNameAndStatus(String name, ExitStatus status);
}
