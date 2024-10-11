package com.project.board.main.api.repository.board;

import com.project.board.main.api.domain.board.BoardDevelopmentAndStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDevelopmentAndStackRepository extends JpaRepository<BoardDevelopmentAndStack, Long> {
    BoardDevelopmentAndStack findBoardDevelopmentAndStackByDevelopmentStackGuid(String developmentStackGuid);
}
