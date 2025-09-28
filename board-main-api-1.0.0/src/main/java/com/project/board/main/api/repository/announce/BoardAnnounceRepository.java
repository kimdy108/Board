package com.project.board.main.api.repository.announce;

import com.project.board.main.api.domain.announce.BoardAnnounce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardAnnounceRepository extends JpaRepository<BoardAnnounce, Long> {
    BoardAnnounce findBoardAnnounceByAnnouceGuid(String announceGuid);

    @Modifying
    @Query("update BoardAnnounce set useFlag = false where memberGuid = :memberGuid")
    void updateUseFlag(String memberGuid);
}
