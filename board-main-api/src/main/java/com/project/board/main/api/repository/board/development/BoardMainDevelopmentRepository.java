package com.project.board.main.api.repository.board.development;

import com.project.board.main.api.domain.board.BoardMainDevelopment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface BoardMainDevelopmentRepository extends JpaRepository<BoardMainDevelopment, Long>, BoardMainDevelopmentRepositoryCustom {
    BoardMainDevelopment findBoardMainDevelopmentByDevelopmentUUID(UUID developmentUUID);
}
