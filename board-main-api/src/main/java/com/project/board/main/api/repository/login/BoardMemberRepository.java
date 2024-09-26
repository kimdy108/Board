package com.project.board.main.api.repository.login;

import com.project.board.main.api.domain.login.BoardMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BoardMemberRepository extends JpaRepository<BoardMember, Long> {
    Optional<BoardMember> findBoardMemberByMemberIdAndMemberPhone(String memberId, String memberName);
    Optional<BoardMember> findBoardMemberByMemberGuid(String memberGuid);
}
