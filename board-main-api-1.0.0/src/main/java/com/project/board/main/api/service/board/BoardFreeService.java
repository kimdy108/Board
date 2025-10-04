package com.project.board.main.api.service.post;

import com.project.board.main.api.domain.post.BoardFree;
import com.project.board.main.api.dto.post.BoardList;
import com.project.board.main.api.dto.post.BoardListInfo;
import com.project.board.main.api.dto.post.BoardRegist;
import com.project.board.main.api.dto.post.BoardUpdate;
import com.project.board.main.api.repository.post.BoardFreeCommentRepository;
import com.project.board.main.api.repository.post.BoardFreeRepository;
import com.project.board.main.api.repository.post.BoardFreeRepositoryImpl;
import com.project.board.main.api.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardFreeService {
    private final BoardFreeRepository boardFreeRepository;
    private final BoardFreeRepositoryImpl boardFreeRepositoryImpl;

    private final BoardFreeCommentRepository boardFreeCommentRepository;

    @Transactional
    public BoardListInfo getFree(String freeGuid) {
        BoardListInfo boardListInfo = boardFreeRepositoryImpl.findBoardFree(freeGuid);
        boardListInfo.setCommentCount(boardFreeCommentRepository.countBoardFreeComment(freeGuid));
        return boardListInfo;
    }

    @Transactional
    public List<BoardList> getFreeListAll() {
        List<BoardList> boardLists = boardFreeRepositoryImpl.findBoardFreeListAll();

        for (BoardList boardList : boardLists) {
            boardList.setBoardCommentCount(boardFreeCommentRepository.countBoardFreeComment(boardList.getBoardGuid()));
        }

        return boardLists;
    }

    @Transactional
    public void registFree(BoardRegist boardRegist, String encryptMemberGuid) {
        try {
            BoardFree boardFree = BoardFree.regist(Common.decryptStringSalt(encryptMemberGuid),
                    UUID.randomUUID().toString(),
                    boardRegist.getBoardTitle(),
                    boardRegist.getBoardContent());
            boardFreeRepository.save(boardFree);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("boardRegist error", e);
        }
    }

    @Transactional
    public void updateFree(BoardUpdate boardUpdate) {
        try {
            BoardFree boardFree = boardFreeRepository.findBoardFreeByFreeGuid(boardUpdate.getBoardGuid());
            boardFree.update(boardUpdate.getBoardTitle(),
                    boardUpdate.getBoardContent());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("boardUpdate error", e);
        }
    }

    @Transactional
    public void deleteFree(String freeGuid) {
        try {
            BoardFree boardFree = boardFreeRepository.findBoardFreeByFreeGuid(freeGuid);
            boardFree.updateUseFlag(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("boardFree error", e);
        }
    }
}
