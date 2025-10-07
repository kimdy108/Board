package com.project.board.main.api.service.qna;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.domain.qna.BoardMainQnA;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.qna.qna.QnAInfo;
import com.project.board.main.api.dto.qna.qna.QnAListPage;
import com.project.board.main.api.dto.qna.qna.QnARegist;
import com.project.board.main.api.dto.qna.qna.QnAUpdate;
import com.project.board.main.api.repository.member.BoardMainMemberRepository;
import com.project.board.main.api.repository.qna.BoardMainQnARepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QnAService {
    private final BoardMainMemberRepository boardMainMemberRepository;
    private final BoardMainQnARepository boardMainQnARepository;

    @Transactional
    public void qnaRegist(QnARegist qnaRegist, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        boardMainQnARepository.save(BoardMainQnA.builder()
                .qnaTitle(qnaRegist.getQnaTitle())
                .qnaContent(qnaRegist.getQnaContent())
                .boardMainMember(boardMainMember)
                .build());
    }

    @Transactional
    public void qnaUpdate(QnAUpdate qnaUpdate, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        BoardMainQnA boardMainQnA = boardMainQnARepository.findBoardMainQnAByQnaUUID(qnaUpdate.getQnaUUID())
                .orElseThrow(() -> new RuntimeException("존재하는 QnA 가 없습니다."));

        if (!boardMainMember.equals(boardMainQnA.getBoardMainMember())) throw new RuntimeException("수정할 수 있는 권한이 없습니다.");

        boardMainQnA.update(qnaUpdate.getQnaTitle(), qnaUpdate.getQnaContent());
    }

    @Transactional
    public void qnaDelete(UUID qnaUUID, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        BoardMainQnA boardMainQnA = boardMainQnARepository.findBoardMainQnAByQnaUUID(qnaUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 QnA 가 없습니다."));

        if (!boardMainMember.equals(boardMainQnA.getBoardMainMember())) throw new RuntimeException("수정할 수 있는 권한이 없습니다.");

        boardMainQnA.updateStatus(IsYesNo.NO);
    }

    @Transactional
    public QnAInfo qnaInfo(UUID qnaUUID) {
        BoardMainQnA boardMainQnA = boardMainQnARepository.findBoardMainQnAByQnaUUID(qnaUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 QnA가 없습니다."));
        return QnAInfo.create(boardMainQnA);
    }

    public QnAListPage qnaListPage(String searchType, String searchValue, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));
        return boardMainQnARepository.findBoardMainQnAListPage(searchType, searchValue, boardMainMember);
    }
}
