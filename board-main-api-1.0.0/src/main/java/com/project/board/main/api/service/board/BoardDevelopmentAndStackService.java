package com.project.board.main.api.service.post;

import com.project.board.main.api.domain.post.BoardDevelopmentAndStack;
import com.project.board.main.api.dto.post.BoardList;
import com.project.board.main.api.dto.post.BoardListInfo;
import com.project.board.main.api.dto.post.BoardRegist;
import com.project.board.main.api.dto.post.BoardUpdate;
import com.project.board.main.api.repository.post.BoardDevelopmentAndStackCommentRepository;
import com.project.board.main.api.repository.post.BoardDevelopmentAndStackRepository;
import com.project.board.main.api.repository.post.BoardDevelopmentAndStackRepositoryImpl;
import com.project.board.main.api.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardDevelopmentAndStackService {
    private final BoardDevelopmentAndStackRepository boardDevelopmentAndStackRepository;
    private final BoardDevelopmentAndStackRepositoryImpl boardDevelopmentAndStackRepositoryImpl;

    private final BoardDevelopmentAndStackCommentRepository boardDevelopmentAndStackCommentRepository;

    @Transactional
    public BoardListInfo getDevelopmentAndStack(String developmentAndStackGuid) {
        BoardListInfo boardListInfo = boardDevelopmentAndStackRepositoryImpl.findBoardDevelopmentAndStack(developmentAndStackGuid);
        boardListInfo.setCommentCount(boardDevelopmentAndStackCommentRepository.countBoardDevelopmentAndStackComment(developmentAndStackGuid));
        return boardListInfo;
    }

    @Transactional
    public List<BoardList> getDevelopmentAndStackListAll() {
        List<BoardList> boardLists = boardDevelopmentAndStackRepositoryImpl.findBoardDevelopmentAndStackListAll();

        for (BoardList boardList : boardLists) {
            boardList.setBoardCommentCount(boardDevelopmentAndStackCommentRepository.countBoardDevelopmentAndStackComment(boardList.getBoardGuid()));
        }

        return boardLists;
    }

    @Transactional
    public void registDevelopmentAndStack(BoardRegist boardRegist, String encryptMemberGuid) {
        try {
            BoardDevelopmentAndStack boardDevelopmentAndStack = BoardDevelopmentAndStack.regist(Common.decryptStringSalt(encryptMemberGuid),
                    UUID.randomUUID().toString(),
                    boardRegist.getBoardTitle(),
                    boardRegist.getBoardContent());
            boardDevelopmentAndStackRepository.save(boardDevelopmentAndStack);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("boardRegist error", e);
        }
    }

    @Transactional
    public void updateDevelopmentAndStack(BoardUpdate boardUpdate) {
        try {
            BoardDevelopmentAndStack boardDevelopmentAndStack = boardDevelopmentAndStackRepository.findBoardDevelopmentAndStackByDevelopmentStackGuid(boardUpdate.getBoardGuid());
            boardDevelopmentAndStack.update(boardUpdate.getBoardTitle(),
                    boardUpdate.getBoardContent());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("boardUpdate error", e);
        }
    }

    @Transactional
    public void deleteDevelopmentAndStack(String developmentAndStackGuid) {
        try {
            BoardDevelopmentAndStack boardDevelopmentAndStack = boardDevelopmentAndStackRepository.findBoardDevelopmentAndStackByDevelopmentStackGuid(developmentAndStackGuid);
            boardDevelopmentAndStack.updateUseFlag(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("boardDelete error", e);
        }
    }
}
