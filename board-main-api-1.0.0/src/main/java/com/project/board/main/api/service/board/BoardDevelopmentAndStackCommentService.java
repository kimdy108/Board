package com.project.board.main.api.service.post;

import com.project.board.main.api.domain.post.BoardDevelopmentAndStackComment;
import com.project.board.main.api.dto.post.BoardComment;
import com.project.board.main.api.dto.post.BoardCommentRegist;
import com.project.board.main.api.dto.post.BoardCommentUpdate;
import com.project.board.main.api.repository.post.BoardDevelopmentAndStackCommentRepository;
import com.project.board.main.api.repository.post.BoardDevelopmentAndStackCommentRepositoryImpl;
import com.project.board.main.api.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardDevelopmentAndStackCommentService {
    private final BoardDevelopmentAndStackCommentRepository boardDevelopmentAndStackCommentRepository;
    private final BoardDevelopmentAndStackCommentRepositoryImpl boardDevelopmentAndStackCommentRepositoryImpl;

    @Transactional
    public List<BoardComment> getBoardCommentList(String developmentStackGuid) {
        return boardDevelopmentAndStackCommentRepositoryImpl.getCommentAll(developmentStackGuid);
    }

    @Transactional
    public void registDevAndStackComment(BoardCommentRegist boardCommentRegist, String encryptMemberGuid) {
        try {
            BoardDevelopmentAndStackComment boardDevelopmentAndStackComment = BoardDevelopmentAndStackComment.regist(UUID.randomUUID().toString(),
                    boardCommentRegist.getComment(),
                    boardCommentRegist.getBoardGuid(),
                    Common.decryptStringSalt(encryptMemberGuid));
            boardDevelopmentAndStackCommentRepository.save(boardDevelopmentAndStackComment);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("boardCommentRegist error", e);
        }
    }

    @Transactional
    public void updateDevAndStackComment(BoardCommentUpdate boardCommentUpdate) {
        try {
            BoardDevelopmentAndStackComment boardDevelopmentAndStackComment = boardDevelopmentAndStackCommentRepository.findBoardDevelopmentAndStackCommentByCommentGuid(boardCommentUpdate.getCommentGuid());
            boardDevelopmentAndStackComment.update(boardCommentUpdate.getCommentString());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("boardCommentDelete error", e);
        }
    }

    @Transactional
    public void deleteDevAndStackComment(String commentGuid) {
        try {
            BoardDevelopmentAndStackComment boardDevelopmentAndStackComment = boardDevelopmentAndStackCommentRepository.findBoardDevelopmentAndStackCommentByCommentGuid(commentGuid);
            boardDevelopmentAndStackComment.updateUseFlag(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("boardCommentDelete error", e);
        }
    }
}
