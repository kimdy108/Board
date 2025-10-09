package com.project.board.main.api.repository.member;

import com.project.board.main.api.domain.member.BoardMainMember;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardMainMemberRepository extends JpaRepository<BoardMainMember, Long>, BoardMainMemberRepositoryCustom {
    Optional<BoardMainMember> findBoardMainMemberByMemberID(String memberID);

    Optional<BoardMainMember> findBoardMainMemberByMemberUUID(UUID memberUUID);
}
