package com.project.board.main.api.repository.member;

import com.project.board.main.api.dto.member.user.UserListPage;

public interface BoardMainMemberRepositoryCustom {
    UserListPage findBoardMainMemberListPage(String searchType, String searchValue, boolean isOverManager);
}
