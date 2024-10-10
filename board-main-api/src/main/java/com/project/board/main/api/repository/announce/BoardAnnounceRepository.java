package com.project.board.main.api.repository.announce;

import com.project.board.main.api.domain.announce.BoardAnnounce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardAnnounceRepository extends JpaRepository<BoardAnnounce, Long> {
    BoardAnnounce findBoardAnnounceByAnnouceGuid(String announceGuid);
}
