package com.project.board.main.api.repository.announce;

import com.project.board.main.api.domain.announce.BoardMainAnnounce;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMainAnnounceRepository extends JpaRepository<BoardMainAnnounce, Long> {
}
