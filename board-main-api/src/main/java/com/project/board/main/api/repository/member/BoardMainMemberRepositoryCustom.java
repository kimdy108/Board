package com.project.board.main.api.repository.member;

import com.project.board.main.api.dto.member.admin.AdminListPage;
import com.project.board.main.api.dto.member.user.UserListPage;

public interface BoardMainMemberRepositoryCustom {
    UserListPage findBoardMainMemberUserListPage(String searchType, String searchValue);

    AdminListPage findBoardMainMemberAdminListPage(String searchType, String searchValue);
}
