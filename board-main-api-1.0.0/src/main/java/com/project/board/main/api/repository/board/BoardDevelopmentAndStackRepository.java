package com.project.board.main.api.repository.post;

import com.project.board.main.api.domain.post.BoardDevelopmentAndStack;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDevelopmentAndStackRepository extends JpaRepository<BoardDevelopmentAndStack, Long> {
    BoardDevelopmentAndStack findBoardDevelopmentAndStackByDevelopmentStackGuid(String developmentStackGuid);

    @Modifying
    @Query("update BoardDevelopmentAndStack set useFlag = false where memberGuid = :memberGuid")
    void updateUseFlag(String memberGuid);
}
