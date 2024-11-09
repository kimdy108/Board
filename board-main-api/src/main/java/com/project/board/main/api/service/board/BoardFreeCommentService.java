package com.project.board.main.api.service.board;

import com.project.board.main.api.domain.board.BoardFreeComment;
import com.project.board.main.api.dto.board.BoardComment;
import com.project.board.main.api.dto.board.BoardCommentRegist;
import com.project.board.main.api.dto.board.BoardCommentUpdate;
import com.project.board.main.api.repository.board.BoardFreeCommentRepository;
import com.project.board.main.api.repository.board.BoardFreeCommentRepositoryImpl;
import com.project.board.main.api.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardFreeCommentService {
    private final BoardFreeCommentRepository boardFreeCommentRepository;
    private final BoardFreeCommentRepositoryImpl boardFreeCommentRepositoryImpl;

    @Transactional
    public List<BoardComment> getBoardCommentList(String freeGuid) {
        return boardFreeCommentRepositoryImpl.getCommentAll(freeGuid);
    }

    @Transactional
    public void registFreeComment(BoardCommentRegist boardCommentRegist, String encryptMemberGuid) {
        try {
            BoardFreeComment boardFreeComment = BoardFreeComment.regist(UUID.randomUUID().toString(),
                    boardCommentRegist.getComment(),
                    boardCommentRegist.getBoardGuid(),
                    Common.decryptStringSalt(encryptMemberGuid));
            boardFreeCommentRepository.save(boardFreeComment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("boardCommentRegist error", e);
        }
    }

    @Transactional
    public void updateFreeComment(BoardCommentUpdate boardCommentUpdate) {
        try {
            BoardFreeComment boardFreeComment = boardFreeCommentRepository.findBoardFreeCommentByCommentGuid(boardCommentUpdate.getCommentGuid());
            boardFreeComment.update(boardCommentUpdate.getCommentString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("boardCommentDelete error", e);
        }
    }

    @Transactional
    public void deleteFreeComment(String commentGuid) {
        try {
            BoardFreeComment boardFreeComment = boardFreeCommentRepository.findBoardFreeCommentByCommentGuid(commentGuid);
            boardFreeComment.updateUseFlag(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("boardCommentDelete error", e);
        }
    }
}
