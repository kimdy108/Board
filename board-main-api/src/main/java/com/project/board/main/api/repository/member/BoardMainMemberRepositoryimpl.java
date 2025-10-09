package com.project.board.main.api.repository.member;

import com.project.board.main.api.domain.member.QBoardMainMember;
import com.project.board.main.api.dto.constant.member.MemberApprovalType;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import static com.project.board.main.api.utils.Common.encryptString;

@Repository
public class BoardMainMemberRepositoryimpl implements BoardMainMemberRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardMainMemberRepositoryimpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardMainMember qBoardMainMember = QBoardMainMember.boardMainMember;

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
        if (!searchType.equals("userRole") || "".equals(searchValue)) return null;
        return qBoardMainMember.memberRole.eq(MemberRole.getValue(searchValue));
    }
}
