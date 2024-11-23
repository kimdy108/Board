package com.project.board.main.api.repository.board;

import com.project.board.main.api.domain.board.BoardDevelopmentAndStackComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDevelopmentAndStackCommentRepository extends JpaRepository<BoardDevelopmentAndStackComment, Long> {
    BoardDevelopmentAndStackComment findBoardDevelopmentAndStackCommentByCommentGuid(String commentGuid);

    @Modifying
    @Query("update BoardDevelopmentAndStackComment set useFlag = false where memberGuid = :memberGuid")
    void updateUseFlag(String memberGuid);
}
