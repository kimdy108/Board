package com.project.board.main.api.repository.announce;

import com.project.board.main.api.domain.announce.BoardMainAnnounce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardMainAnnounceRepository extends JpaRepository<BoardMainAnnounce, Long>, BoardMainAnnounceRepositoryCustom {
    Optional<BoardMainAnnounce> findBoardMainAnnounceByAnnounceUUID(UUID announceUUID);
}
