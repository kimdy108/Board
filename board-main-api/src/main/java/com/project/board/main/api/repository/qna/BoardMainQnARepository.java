package com.project.board.main.api.repository.qna;

import com.project.board.main.api.domain.qna.BoardMainQnA;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardMainQnARepository extends JpaRepository<BoardMainQnA, Long>, BoardMainQnARepositoryCustom {
    Optional<BoardMainQnA> findBoardMainQnAByQnaUUID(UUID qnaUUID);
}
