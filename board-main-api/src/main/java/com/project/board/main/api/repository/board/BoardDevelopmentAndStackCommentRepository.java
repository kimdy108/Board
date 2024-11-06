package com.project.board.main.api.repository.board;

import com.project.board.main.api.domain.board.BoardDevelopmentAndStackComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardDevelopmentAndStackCommentRepository extends JpaRepository<BoardDevelopmentAndStackComment, Long> {
}
