package com.project.board.main.api.repository.announce;

import com.project.board.main.api.dto.announce.AnnounceListPage;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMainAnnounceRepositoryCustom {
    AnnounceListPage findBoardMainAnnounceListPage(String searchType, String searchValue);
}
