package com.study.board.repository;

import com.study.board.entity.Member;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MemberRepository extends JpaRepository<Member, Long> {

    Member findByIdAndPassword(String id, String password);

    Member findById(String id);

    Member findByNickname(String nickname);

    Member findByPhoneNum(String phoneNum);

    Member findByUsernameAndPhoneNum(String username, String phoneNum);

    Member findByIdAndUsernameAndPhoneNum(String id, String username, String phoneNum);
}
