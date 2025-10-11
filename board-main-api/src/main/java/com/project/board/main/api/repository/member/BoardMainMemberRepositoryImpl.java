package com.project.board.main.api.repository.member;

import com.project.board.main.api.domain.member.QBoardMainMember;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberApprovalType;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.dto.member.admin.AdminList;
import com.project.board.main.api.dto.member.admin.AdminListPage;
import com.project.board.main.api.dto.member.user.UserList;
import com.project.board.main.api.dto.member.user.UserListPage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.board.main.api.utils.Common.*;

@Repository
public class BoardMainMemberRepositoryImpl implements BoardMainMemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardMainMemberRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardMainMember qBoardMainMember = QBoardMainMember.boardMainMember;

    @Override
    public UserListPage findBoardMainMemberUserListPage(String searchType, String searchValue) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qBoardMainMember.memberType.eq("NORMAL"));
        bb.and(qBoardMainMember.isActive.eq(IsYesNo.YES));
        bb.and(qBoardMainMember.memberRole.eq(MemberRole.MEMBER));

        List<UserList> userLists = jpaQueryFactory
                .select(Projections.fields(
                        UserList.class,
                        qBoardMainMember.memberUUID.as("userUUID"),
                        qBoardMainMember.memberID.as("userID"),
                        qBoardMainMember.memberName.as("userName"),
                        qBoardMainMember.memberNickName.as("userNickName"),
                        qBoardMainMember.memberApproval.as("userApproval"),
                        qBoardMainMember.insertDate.as("insertDate")
                ))
                .from(qBoardMainMember)
                .where(bb, eqMemberID(searchType, searchValue), eqMemberName(searchType, searchValue))
                .orderBy(qBoardMainMember.seq.desc())
                .fetch();

        Long count = jpaQueryFactory
                .select(qBoardMainMember.seq.count())
                .from(qBoardMainMember)
                .where(bb, eqMemberID(searchType, searchValue), eqMemberName(searchType, searchValue), eqMemberApproval(searchType, searchValue))
                .fetchOne();

        for (UserList userList : userLists) {
            userList.setUserName(decryptString(userList.getUserName()));
        }

        return UserListPage.builder()
                .userContents(userLists)
                .totalCount(count)
                .build();
    }

    @Override
    public AdminListPage findBoardMainMemberAdminListPage(String searchType, String searchValue) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qBoardMainMember.memberType.eq("NORMAL"));
        bb.and(qBoardMainMember.isActive.eq(IsYesNo.YES));
        bb.and(qBoardMainMember.memberRole.ne(MemberRole.MEMBER));
        bb.and(qBoardMainMember.memberApproval.eq(MemberApprovalType.APPROVE));

        List<AdminList> adminLists = jpaQueryFactory
                .select(Projections.fields(
                        AdminList.class,
                        qBoardMainMember.memberUUID.as("adminUUID"),
                        qBoardMainMember.memberID.as("adminID"),
                        qBoardMainMember.memberName.as("adminName"),
                        qBoardMainMember.memberNickName.as("adminNickName"),
                        qBoardMainMember.memberRole.as("adminRole"),
                        qBoardMainMember.insertDate.as("insertDate")
                ))
                .from(qBoardMainMember)
                .where(bb, eqMemberID(searchType, searchValue), eqMemberName(searchType, searchValue), eqMemberRole(searchType, searchValue))
                .orderBy(qBoardMainMember.seq.desc())
                .fetch();

        Long count = jpaQueryFactory
                .select(qBoardMainMember.seq.count())
                .from(qBoardMainMember)
                .where(bb, eqMemberID(searchType, searchValue), eqMemberName(searchType, searchValue), eqMemberRole(searchType, searchValue))
                .fetchOne();

        for (AdminList adminList : adminLists) {
            adminList.setAdminName(decryptString(adminList.getAdminName()));
        }

        return AdminListPage.builder()
                .adminContents(adminLists)
                .totalCount(count)
                .build();
    }

    private BooleanExpression eqMemberID(String searchType, String searchValue) {
        if (!searchType.equals("memberID") || "".equals(searchValue)) return null;
        return qBoardMainMember.memberID.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqMemberName(String searchType, String searchValue) {
        if (!searchType.equals("memberName") || "".equals(searchValue)) return null;
        return qBoardMainMember.memberName.eq(encryptString(searchValue));
    }

    private BooleanExpression eqMemberApproval(String searchType, String searchValue) {
        if (!searchType.equals("memberApproval") || "".equals(searchValue)) return null;
        return qBoardMainMember.memberApproval.eq(MemberApprovalType.fromValue(searchValue));
    }

    private BooleanExpression eqMemberRole(String searchType, String searchValue) {
        if (!searchType.equals("memberRole") || "".equals(searchValue) || searchValue.equalsIgnoreCase("MEMBER")) return null;
        return qBoardMainMember.memberRole.eq(MemberRole.getValue(searchValue));
    }
}
