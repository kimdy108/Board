package com.project.board.main.api.repository.member;

import com.project.board.main.api.domain.member.QBoardMainMember;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberApprovalType;
import com.project.board.main.api.dto.constant.member.MemberRole;
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
    public UserListPage findBoardMainMemberListPage(String searchType, String searchValue, boolean isOverManager) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qBoardMainMember.memberType.eq("NORMAL"));
        bb.and(qBoardMainMember.isActive.eq(IsYesNo.YES));
        if (isOverManager) bb.and(eqMemberRole(searchType, searchValue));
        else bb.and(eqMemberApproval(searchType, searchValue));

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
                .where(bb, eqMemberID(searchType, searchValue), eqMemberName(searchType, searchValue))
                .fetchOne();

        for (UserList userList : userLists) {
            userList.setUserName(decryptString(userList.getUserName()));
        }

        return UserListPage.builder()
                .userContents(userLists)
                .totalCount(count)
                .build();
    }

    private BooleanExpression eqMemberID(String searchType, String searchValue) {
        if (!searchType.equals("userID") || "".equals(searchValue)) return null;
        return qBoardMainMember.memberID.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqMemberName(String searchType, String searchValue) {
        if (!searchType.equals("userName") || "".equals(searchValue)) return null;
        return qBoardMainMember.memberName.eq(encryptString(searchValue));
    }

    private BooleanExpression eqMemberApproval(String searchType, String searchValue) {
        if (!searchType.equals("userApproval") || "".equals(searchValue)) return null;
        return qBoardMainMember.memberApproval.eq(MemberApprovalType.fromValue(searchValue));
    }

    private BooleanExpression eqMemberRole(String searchType, String searchValue) {
        if (!searchType.equals("userRole") || "".equals(searchValue) || searchValue.equalsIgnoreCase("MEMBER")) return null;
        return qBoardMainMember.memberRole.eq(MemberRole.getValue(searchValue));
    }
}
