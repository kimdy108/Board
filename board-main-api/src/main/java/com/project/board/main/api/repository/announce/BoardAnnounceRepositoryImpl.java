package com.project.board.main.api.repository.announce;

import com.project.board.main.api.domain.announce.BoardAnnounce;
import com.project.board.main.api.domain.announce.QBoardAnnounce;
import com.project.board.main.api.domain.member.QBoardMember;
import com.project.board.main.api.dto.announce.BoardNotice;
import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.jpa.repository.support.QuerydslRepositorySupport;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardAnnounceRepositoryImpl extends QuerydslRepositorySupport {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardAnnounceRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        super(BoardAnnounce.class);
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardAnnounce qboardAnnounce = QBoardAnnounce.boardAnnounce;
    QBoardMember qboardMember = QBoardMember.boardMember;

    public BoardNotice findBoardAnnounce(String noticeGuid) {
        return jpaQueryFactory
                .select(Projections.fields(
                        BoardNotice.class,
                        qboardAnnounce.annouceGuid.as("noticeGuid"),
                        qboardAnnounce.announceTitle.as("noticeTitle"),
                        qboardAnnounce.announceContent.as("noticeContent"),
                        qboardMember.memberGuid.as("memberGuid"),
                        qboardMember.memberNickName.as("memberNickName"),
                        qboardAnnounce.insertDate.as("noticeInsertDate")
                ))
                .from(qboardAnnounce)
                .innerJoin(qboardMember).on(qboardAnnounce.memberGuid.eq(qboardMember.memberGuid))
                .where(qboardAnnounce.annouceGuid.eq(noticeGuid))
                .orderBy(qboardAnnounce.insertDate.asc())
                .fetchOne();
    }

    public List<BoardNotice> findBoardAnnounceListAll() {
        return jpaQueryFactory
                .select(Projections.fields(
                        BoardNotice.class,
                        qboardAnnounce.annouceGuid.as("noticeGuid"),
                        qboardAnnounce.announceTitle.as("noticeTitle"),
                        qboardAnnounce.announceContent.as("noticeContent"),
                        qboardMember.memberGuid.as("memberGuid"),
                        qboardMember.memberNickName.as("memberNickName"),
                        qboardAnnounce.insertDate.as("noticeInsertDate")
                ))
                .from(qboardAnnounce)
                .innerJoin(qboardMember).on(qboardAnnounce.memberGuid.eq(qboardMember.memberGuid))
                .where(qboardAnnounce.useFlag.eq(true))
                .orderBy(qboardAnnounce.insertDate.asc())
                .fetch();
    }
}
