package com.project.board.main.api.repository.qna;

import com.project.board.main.api.domain.qna.BoardQnA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardQnARepository extends JpaRepository<BoardQnA, Long> {
    BoardQnA findBoardQnAByQnaGuid(String qnaGuid);
}
