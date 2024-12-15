package com.project.board.main.api.service.board;

import com.project.board.main.api.domain.board.BoardFree;
import com.project.board.main.api.dto.board.BoardList;
import com.project.board.main.api.dto.board.BoardListInfo;
import com.project.board.main.api.dto.board.BoardRegist;
import com.project.board.main.api.dto.board.BoardUpdate;
import com.project.board.main.api.repository.board.BoardFreeCommentRepository;
import com.project.board.main.api.repository.board.BoardFreeRepository;
import com.project.board.main.api.repository.board.BoardFreeRepositoryImpl;
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
        return boardFreeRepositoryImpl.findBoardFreeListAll();
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
