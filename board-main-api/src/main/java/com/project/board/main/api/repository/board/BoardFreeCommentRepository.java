package com.project.board.main.api.repository.board;

import com.project.board.main.api.domain.board.BoardFreeComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardFreeCommentRepository extends JpaRepository<BoardFreeComment, Long> {
    BoardFreeComment findBoardFreeCommentByCommentGuid(String commentGuid);
}
