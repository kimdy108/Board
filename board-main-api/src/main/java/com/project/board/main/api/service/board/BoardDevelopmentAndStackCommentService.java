package com.project.board.main.api.service.board;

import com.project.board.main.api.dto.board.BoardComment;
import com.project.board.main.api.repository.board.BoardDevelopmentAndStackCommentRepository;
import com.project.board.main.api.repository.board.BoardDevelopmentAndStackCommentRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardDevelopmentAndStackCommentService {
    private final BoardDevelopmentAndStackCommentRepository boardDevelopmentAndStackCommentRepository;
    private final BoardDevelopmentAndStackCommentRepositoryImpl boardDevelopmentAndStackCommentRepositoryImpl;

    @Transactional
    public List<BoardComment> getBoardCommentList(String developmentStackGuid) {
        return boardDevelopmentAndStackCommentRepositoryImpl.getCommentAll(developmentStackGuid);
    }
}
