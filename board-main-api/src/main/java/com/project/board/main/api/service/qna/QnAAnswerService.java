package com.project.board.main.api.service.qna;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.domain.qna.BoardMainQnA;
import com.project.board.main.api.domain.qna.BoardMainQnAAnswer;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.dto.qna.answer.QnAAnswerInfo;
import com.project.board.main.api.dto.qna.answer.QnAAnswerRegist;
import com.project.board.main.api.dto.qna.answer.QnAAnswerUpdate;
import com.project.board.main.api.repository.member.BoardMainMemberRepository;
import com.project.board.main.api.repository.qna.BoardMainQnAAnswerRepository;
import com.project.board.main.api.repository.qna.BoardMainQnARepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class QnAAnswerService {
    private final BoardMainMemberRepository boardMainMemberRepository;
    private final BoardMainQnARepository boardMainQnARepository;
    private final BoardMainQnAAnswerRepository boardMainQnAAnswerRepository;

    @Transactional
    public void answerRegist(QnAAnswerRegist qnaAnswerRegist, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        if (!MemberRole.isOverManager(boardMainMember.getMemberRole())) throw new RuntimeException("등록할 수 있는 권한이 없습니다.");

        BoardMainQnA boardMainQnA = boardMainQnARepository.findBoardMainQnAByQnaUUID(qnaAnswerRegist.getQnaUUID())
                .orElseThrow(() -> new RuntimeException("존재하는 QnA 가 없습니다."));

        boardMainQnA.updateIsAnswer();

        boardMainQnAAnswerRepository.save(BoardMainQnAAnswer.builder()
                .answerContent(qnaAnswerRegist.getAnswerContent())
                .boardMainMember(boardMainMember)
                .boardMainQnA(boardMainQnA)
                .build());
    }

    @Transactional
    public void answerUpdate(QnAAnswerUpdate qnaAnswerUpdate, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        if (!MemberRole.isOverManager(boardMainMember.getMemberRole())) throw new RuntimeException("수정할 수 있는 권한이 없습니다.");

        BoardMainQnAAnswer boardMainQnAAnswer = boardMainQnAAnswerRepository.findBoardMainQnAAnswerByAnswerUUID(qnaAnswerUpdate.getAnswerUUID())
                .orElseThrow(() -> new RuntimeException("존재하는 답변이 없습니다."));

        boardMainQnAAnswer.update(qnaAnswerUpdate.getAnswerContent());
    }

    @Transactional
    public void answerDelete(UUID answerUUID, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        if (!MemberRole.isOverManager(boardMainMember.getMemberRole())) throw new RuntimeException("삭제할 수 있는 권한이 없습니다.");

        BoardMainQnAAnswer boardMainQnAAnswer = boardMainQnAAnswerRepository.findBoardMainQnAAnswerByAnswerUUID(answerUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 답변이 없습니다."));

        boardMainQnAAnswer.updateStatus(IsYesNo.NO);
    }

    public QnAAnswerInfo getAnswerInfo(UUID answerUUID, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        if (!MemberRole.isOverManager(boardMainMember.getMemberRole())) throw new RuntimeException("수정할 수 있는 권한이 없습니다.");

        BoardMainQnAAnswer boardMainQnAAnswer = boardMainQnAAnswerRepository.findBoardMainQnAAnswerByAnswerUUID(answerUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 답변이 없습니다."));

        return QnAAnswerInfo.create(boardMainQnAAnswer);
    }
}
