package com.project.board.main.api.repository.announce;

import com.project.board.main.api.domain.announce.BoardMainAnnounce;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.UUID;

@Repository
public interface BoardMainAnnounceRepository extends JpaRepository<BoardMainAnnounce, Long>, BoardMainAnnounceRepositoryCustom {
    BoardMainAnnounce findBoardMainAnnounceByAnnounceUUID(UUID announceUUID);

    @Query(value = "update BoardMainAnnounce set isActive = :isActive, updateDate = :updateDate where announceUUID = :announceUUID")
    @Modifying
    @Transactional
    void updateBoardMainAnnounceByActiveStatus(UUID announceUUID, IsYesNo isActive, LocalDateTime updateDate);

    @Query(value = "update BoardMainAnnounce set viewCounter = viewCounter + 1 where announceUUID = :announceUUID")
    @Modifying
    @Transactional
    void updateBoardMainAnnounceByViewCounter(UUID announceUUID);
}
