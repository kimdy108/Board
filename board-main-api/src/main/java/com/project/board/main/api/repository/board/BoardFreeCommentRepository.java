package com.project.board.main.api.repository.board;

import com.project.board.main.api.domain.board.BoardFreeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardFreeCommentRepository extends JpaRepository<BoardFreeComment, Long> {
    BoardFreeComment findBoardFreeCommentByCommentGuid(String commentGuid);

    @Modifying
    @Query("update BoardFreeComment set useFlag = false where memberGuid = :memberGuid")
    void updateUseFlag(String memberGuid);
}
