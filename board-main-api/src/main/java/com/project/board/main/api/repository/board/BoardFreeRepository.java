package com.project.board.main.api.repository.board;

import com.project.board.main.api.domain.board.BoardFree;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardFreeRepository extends JpaRepository<BoardFree, Long> {
    BoardFree findBoardFreeByFreeGuid(String freeGuid);
}
