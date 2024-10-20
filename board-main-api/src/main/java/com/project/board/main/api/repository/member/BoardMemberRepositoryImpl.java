package com.project.board.main.api.repository.member;

import com.project.board.main.api.domain.announce.BoardAnnounce;
import com.project.board.main.api.domain.member.QBoardMember;
import com.project.board.main.api.dto.member.BoardMemberInfoAll;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardMemberRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardMemberRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(BoardAnnounce.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardMember qboardMember = QBoardMember.boardMember;

    public List<BoardMemberInfoAll> getBoardMemberInfoAll() {
        return jpaQueryFactory
                .select(Projections.fields(
                        BoardMemberInfoAll.class,
                        qboardMember.memberId.as("userId"),
                        qboardMember.memberGuid.as("userGuid"),
                        qboardMember.memberName.as("userName"),
                        qboardMember.memberNickName.as("userNickName"),
                        qboardMember.memberEmail.as("userEmail"),
                        qboardMember.memberPhone.as("userPhone"),
                        qboardMember.memberRole.as("userRole"),
                        qboardMember.insertDate.as("insertDate")
                ))
                .from(qboardMember)
                .where(qboardMember.useFlag.eq(true))
                .orderBy(qboardMember.memberRole.asc())
                .fetch();
    }
}
