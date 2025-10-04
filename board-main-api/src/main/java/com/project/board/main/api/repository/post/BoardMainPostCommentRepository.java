package com.project.board.main.api.repository.post;

import com.project.board.main.api.domain.post.BoardMainPostComment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardMainPostCommentRepository extends JpaRepository<BoardMainPostComment, Long>, BoardMainPostCommentRepositoryCustom {
    Optional<BoardMainPostComment> findBoardMainPostCommentByCommentUUID(UUID commentUUID);
}
