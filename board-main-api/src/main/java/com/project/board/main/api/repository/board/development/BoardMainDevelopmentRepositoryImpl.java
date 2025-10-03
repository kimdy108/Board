package com.project.board.main.api.repository.board.development;

import com.project.board.main.api.domain.board.QBoardMainDevelopment;
import com.project.board.main.api.dto.board.development.DevelopmentList;
import com.project.board.main.api.dto.board.development.DevelopmentListPage;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.board.main.api.utils.Common.initDecryptStr;
import static com.project.board.main.api.utils.Common.initEncryptStr;

@Repository
public class BoardMainDevelopmentRepositoryImpl implements BoardMainDevelopmentRepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardMainDevelopmentRepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardMainDevelopment qBoardMainDevelopment = QBoardMainDevelopment.boardMainDevelopment;

    @Override
    public DevelopmentListPage findDevelopmentListPage(String searchType, String searchValue) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qBoardMainDevelopment.isActive.eq(IsYesNo.YES));

        List<DevelopmentList> developmentLists = jpaQueryFactory
                .select(Projections.fields(
                        DevelopmentList.class,
                        qBoardMainDevelopment.developmentUUID.as("developmentUUID"),
                        qBoardMainDevelopment.developmentTitle.as("developmentTitle"),
                        qBoardMainDevelopment.boardMainMember.memberName.as("authorName"),
                        qBoardMainDevelopment.viewCounter.as("viewCounter"),
                        qBoardMainDevelopment.insertDate.as("insertDate")
                ))
                .from(qBoardMainDevelopment)
                .where(bb, eqDevelopmentTitle(searchType, searchValue), eqDevelopmentContent(searchType, searchValue), eqAuthorName(searchType, searchValue))
                .orderBy(qBoardMainDevelopment.seq.desc())
                .fetch();

        Long count = jpaQueryFactory
                .select(qBoardMainDevelopment.seq.count())
                .from(qBoardMainDevelopment)
                .where(bb, eqDevelopmentTitle(searchType, searchValue), eqDevelopmentContent(searchType, searchValue), eqAuthorName(searchType, searchValue))
                .fetchOne();

        for (DevelopmentList developmentList : developmentLists) {
            developmentList.setAuthorName(initDecryptStr(developmentList.getAuthorName()));
        }

        return DevelopmentListPage.builder()
                .developmentContents(developmentLists)
                .totalCount(count)
                .build();
    }

    private BooleanExpression eqDevelopmentTitle(String searchType, String searchValue) {
        if (!searchType.equals("developmentTitle") || "".equals(searchValue)) return null;
        return qBoardMainDevelopment.developmentTitle.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqDevelopmentContent(String searchType, String searchValue) {
        if (!searchType.equals("developmentContent") || "".equals(searchValue)) return null;
        return qBoardMainDevelopment.developmentContent.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqAuthorName(String searchType, String searchValue) {
        if (!searchType.equals("authorName") || "".equals(searchValue)) return null;
        return qBoardMainDevelopment.boardMainMember.memberName.eq(initEncryptStr(searchValue));
    }
}
