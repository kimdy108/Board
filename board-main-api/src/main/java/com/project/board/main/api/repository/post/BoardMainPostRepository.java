package com.project.board.main.api.repository.post;

import com.project.board.main.api.domain.post.BoardMainPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface BoardMainPostRepository extends JpaRepository<BoardMainPost, Long>, BoardMainPostRepositoryCustom {
    Optional<BoardMainPost> findBoardMainPostByPostUUID(UUID postUUID);
}
