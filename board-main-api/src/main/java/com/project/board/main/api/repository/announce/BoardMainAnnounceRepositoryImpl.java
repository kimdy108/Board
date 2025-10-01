package com.project.board.main.api.repository.announce;

import com.project.board.main.api.domain.announce.QBoardMainAnnounce;
import com.project.board.main.api.domain.member.QBoardMainMember;
import com.project.board.main.api.dto.announce.AnnounceList;
import com.project.board.main.api.dto.announce.AnnounceListPage;
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
    QBoardMainMember qBoardMainMember = QBoardMainMember.boardMainMember;

    @Override
    public AnnounceListPage findBoardMainAnnounceListPage(String searchType, String searchValue) {
        List<AnnounceList> announceLists = jpaQueryFactory
                .select(Projections.fields(
                        AnnounceList.class,
                        qBoardMainAnnounce.announceUUID.as("announceUUID"),
                        qBoardMainAnnounce.announceTitle.as("announceTitle"),
                        qBoardMainAnnounce.viewCounter.as("viewCounter"),
                        qBoardMainAnnounce.insertDate.as("insertDate"),
                        qBoardMainAnnounce.updateDate.as("updateDate")
                ))
                .from(qBoardMainAnnounce)
                .leftJoin(qBoardMainAnnounce.boardMainMember, qBoardMainMember)
                .where(eqAnnounceTitle(searchType, searchValue), eqAnnounceContent(searchType, searchValue))
                .fetch();

        Long count = jpaQueryFactory
                .select(qBoardMainAnnounce.seq.count())
                .from(qBoardMainAnnounce)
                .leftJoin(qBoardMainAnnounce.boardMainMember, qBoardMainMember)
                .where(eqAnnounceTitle(searchType, searchValue), eqAnnounceContent(searchType, searchValue))
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
