package com.project.board.main.api.repository.qna;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.domain.qna.QBoardMainQnA;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.dto.qna.qna.QnAList;
import com.project.board.main.api.dto.qna.qna.QnAListPage;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.Projections;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.stereotype.Repository;

import java.util.List;

import static com.project.board.main.api.utils.Common.decryptString;
import static com.project.board.main.api.utils.Common.encryptString;

@Repository
public class BoardMainQnARepositoryImpl implements BoardMainQnARepositoryCustom {
    private final JPAQueryFactory jpaQueryFactory;

    public BoardMainQnARepositoryImpl(JPAQueryFactory jpaQueryFactory) {
        this.jpaQueryFactory = jpaQueryFactory;
    }

    QBoardMainQnA qBoardMainQnA = QBoardMainQnA.boardMainQnA;

    @Override
    public QnAListPage findBoardMainQnAListPage(String searchType, String searchValue, BoardMainMember boardMainMember) {
        BooleanBuilder bb = new BooleanBuilder();
        bb.and(qBoardMainQnA.isActive.eq(IsYesNo.YES));
        if (boardMainMember.getMemberRole().equals(MemberRole.MEMBER)) bb.and(qBoardMainQnA.boardMainMember.eq(boardMainMember));

        List<QnAList> qnaLists = jpaQueryFactory
                .select(Projections.fields(
                        QnAList.class,
                        qBoardMainQnA.qnaUUID.as("qnaUUID"),
                        qBoardMainQnA.qnaTitle.as("qnaTitle"),
                        qBoardMainQnA.boardMainMember.memberNickName.as("memberNickName"),
                        qBoardMainQnA.isAnswer.as("isAnswer"),
                        qBoardMainQnA.insertDate.as("insertDate")
                ))
                .from(qBoardMainQnA)
                .where(bb, eqQnATitle(searchType, searchValue), eqQnAContent(searchType, searchValue), eqQnAAuthor(searchType, searchValue))
                .orderBy(qBoardMainQnA.seq.desc())
                .fetch();

        Long count = jpaQueryFactory
                .select(qBoardMainQnA.seq.count())
                .from(qBoardMainQnA)
                .where(bb, eqQnATitle(searchType, searchValue), eqQnAContent(searchType, searchValue), eqQnAAuthor(searchType, searchValue))
                .fetchOne();

        return QnAListPage.builder()
                .qnaContents(qnaLists)
                .totalCount(count)
                .build();
    }

    private BooleanExpression eqQnATitle(String searchType, String searchValue) {
        if (!searchType.equals("qnaTitle") || "".equals(searchValue)) return null;
        return qBoardMainQnA.qnaTitle.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqQnAContent(String searchType, String searchValue) {
        if (!searchType.equals("qnaContent") || "".equals(searchValue)) return null;
        return qBoardMainQnA.qnaContent.containsIgnoreCase(searchValue);
    }

    private BooleanExpression eqQnAAuthor(String searchType, String searchValue) {
        if (!searchType.equals("qnaAuthor") || "".equals(searchValue)) return null;
        return qBoardMainQnA.boardMainMember.memberNickName.eq(encryptString(searchValue));
    }
}
