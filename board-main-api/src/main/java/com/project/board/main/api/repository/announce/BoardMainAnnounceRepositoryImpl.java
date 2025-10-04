package com.project.board.main.api.repository.announce;

import com.project.board.main.api.domain.announce.QBoardMainAnnounce;
import com.project.board.main.api.domain.member.QBoardMainMember;
import com.project.board.main.api.dto.announce.AnnounceList;
import com.project.board.main.api.dto.announce.AnnounceListPage;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class BoardMainAnnounceRepositoryImpl implements BoardMainAnnounceRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardMainAnnounceRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardMainAnnounce qBoardMainAnnounce = QBoardMainAnnounce.boardMainAnnounce;

    @Override
    public AnnounceListPage findBoardMainAnnounceListPage(String searchType, String searchValue) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qBoardMainAnnounce.isActive.eq(IsYesNo.YES));

        List<AnnounceList> announceLists = jpaQueryFactory
                .select(Projections.fields(
                        AnnounceList.class,
                        qBoardMainAnnounce.announceUUID.as("announceUUID"),
                        qBoardMainAnnounce.announceTitle.as("announceTitle"),
                        qBoardMainAnnounce.viewCount.as("viewCount"),
                        qBoardMainAnnounce.insertDate.as("insertDate")
                ))
                .from(qBoardMainAnnounce)
                .where(bb, eqAnnounceTitle(searchType, searchValue), eqAnnounceContent(searchType, searchValue))
                .orderBy(qBoardMainAnnounce.seq.desc())
                .fetch();

        Long count = jpaQueryFactory
                .select(qBoardMainAnnounce.seq.count())
                .from(qBoardMainAnnounce)
                .where(bb, eqAnnounceTitle(searchType, searchValue), eqAnnounceContent(searchType, searchValue))
                .fetchOne();

        return AnnounceListPage.builder()
                .announceContents(announceLists)
                .totalCount(count)
                .build();
    }

    private BooleanExpression eqAnnounceTitle(String searchType, String searchValue) {
        if (!searchType.equals("announceTitle") || "".equals(searchValue)) return null;
        return qBoardMainAnnounce.announceTitle.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqAnnounceContent(String searchType, String searchValue) {
        if (!searchType.equals("announceContent") || "".equals(searchValue)) return null;
        return qBoardMainAnnounce.announceContent.containsIgnoreCase(searchValue);
    }
}
