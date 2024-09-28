package com.project.board.main.api.repository.member;

import com.project.board.main.api.domain.member.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {
    Optional<BoardMember> findBoardMemberByMemberId(String memberId);
    Optional<BoardMember> findBoardMemberByMemberIdAndMemberPhone(String memberId, String memberName);
    Optional<BoardMember> findBoardMemberByMemberGuid(String memberGuid);

    @Query("select count(*) from BoardMember where memberId = :memberId")
    int countBoardMemberByMemberId(String memberId);

    @Query("select count(*) from BoardMember where memberNickName = :memberNickName")
    int countBoardMemberByMemberNickName(String memberNickName);

    @Query("select count(*) from BoardMember where memberPhone = :memberPhone")
    int countBoardMemberByMemberPhone(String memberPhone);
}
