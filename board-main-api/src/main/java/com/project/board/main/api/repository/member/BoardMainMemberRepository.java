package com.project.board.main.api.repository.member;

import com.project.board.main.api.domain.member.BoardMainMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMainMemberRepository extends JpaRepository<BoardMainMember, Long> {
}
