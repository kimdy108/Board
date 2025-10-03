package com.project.board.main.api.repository.board.development;

import com.project.board.main.api.dto.board.development.DevelopmentListPage;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMainDevelopmentRepositoryCustom {
    DevelopmentListPage findDevelopmentListPage(String searchType, String searchValue);
}
