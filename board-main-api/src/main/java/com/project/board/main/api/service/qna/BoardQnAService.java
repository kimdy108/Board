package com.project.board.main.api.service.qna;

import com.project.board.main.api.domain.qna.BoardQnA;
import com.project.board.main.api.dto.qna.BoardQnAList;
import com.project.board.main.api.dto.qna.BoardQnARegist;
import com.project.board.main.api.dto.qna.BoardQnAUpdate;
import com.project.board.main.api.repository.qna.BoardQnARepository;
import com.project.board.main.api.repository.qna.BoardQnARepositoryImpl;
import com.project.board.main.api.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardQnAService {
    private final BoardQnARepository boardQnARepository;
    private final BoardQnARepositoryImpl boardQnARepositoryImpl;

    @Transactional
    public BoardQnAList getQna(String qnaGuid) {
        return boardQnARepositoryImpl.findBoardQnA(qnaGuid);
    }

    @Transactional
    public List<BoardQnAList> getBoardQnaListAll() {
        return boardQnARepositoryImpl.findBoardQnAListAll();
    }

    @Transactional
    public void registQna(BoardQnARegist boardQnARegist, String encryptMemberGuid) {
        try {
            BoardQnA boardQnA = BoardQnA.regist(Common.decryptStringSalt(encryptMemberGuid),
                    UUID.randomUUID().toString(),
                    boardQnARegist.getQnaTitle(),
                    boardQnARegist.getQnaContent());
            boardQnARepository.save(boardQnA);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("qnaResit error", e);
        }
    }

    @Transactional
    public void updateQna(BoardQnAUpdate boardQnAUpdate) {
        try {
            BoardQnA boardQnA = boardQnARepository.findBoardQnAByQnaGuid(boardQnAUpdate.getQnaGuid());
            boardQnA.update(boardQnAUpdate.getQnaTitle(),
                    boardQnAUpdate.getQnaContent());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("qnaResit error", e);
        }
    }

    @Transactional
    public void deleteQna(String qnaGuid) {
        try {
            BoardQnA boardQnA = boardQnARepository.findBoardQnAByQnaGuid(qnaGuid);
            boardQnA.updateUseFlag(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("qnaResit error", e);
        }
    }
}
