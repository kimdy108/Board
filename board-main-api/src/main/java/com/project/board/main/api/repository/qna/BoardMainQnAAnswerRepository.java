package com.project.board.main.api.repository.qna;

import com.project.board.main.api.domain.qna.BoardMainQnAAnswer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardMainQnAAnswerRepository extends JpaRepository<BoardMainQnAAnswer, Long>, BoardMainQnAAnswerRepositoryCustom {
    Optional<BoardMainQnAAnswer> findBoardMainQnAAnswerByAnswerUUID(UUID answerUUID);
}
