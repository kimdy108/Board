package com.project.board.main.api.repository.qna;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.qna.qna.QnAListPage;

public interface BoardMainQnARepositoryCustom {
    QnAListPage findBoardMainQnAListPage(String searchType, String searchValue, BoardMainMember boardMainMember);
}
