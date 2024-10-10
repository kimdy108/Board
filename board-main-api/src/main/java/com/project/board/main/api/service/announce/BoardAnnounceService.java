package com.project.board.main.api.service.announce;

import com.project.board.main.api.domain.announce.BoardAnnounce;
import com.project.board.main.api.dto.announce.BoardNotice;
import com.project.board.main.api.dto.announce.BoardNoticeRegist;
import com.project.board.main.api.dto.announce.BoardNoticeUpdate;
import com.project.board.main.api.repository.announce.BoardAnnounceRepository;
import com.project.board.main.api.repository.announce.BoardAnnounceRepositoryImpl;
import com.project.board.main.api.utils.Common;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class BoardAnnounceService {
    private final BoardAnnounceRepository boardAnnounceRepository;
    private final BoardAnnounceRepositoryImpl boardAnnounceRepositoryImpl;

    @Transactional
    public List<BoardNotice> getAnnounceListAll() {
        return boardAnnounceRepositoryImpl.findBoardAnnounceListAll();
    }

    @Transactional
    public void registAnnounce(BoardNoticeRegist boardNoticeRegist, String encryptMemberGuid) {
        try {
            BoardAnnounce boardAnnounce = BoardAnnounce.regist(UUID.randomUUID().toString(),
                    Common.decryptStringSalt(encryptMemberGuid),
                    boardNoticeRegist.getNoticeTitle(),
                    boardNoticeRegist.getNoticeContent());
            boardAnnounceRepository.save(boardAnnounce);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("noticeRegist error", e);
        }
    }

    @Transactional
    public void updateAnnounce(BoardNoticeUpdate boardNoticeUpdate) {
        try {
            BoardAnnounce boardAnnounce = boardAnnounceRepository.findBoardAnnounceByAnnouceGuid(boardNoticeUpdate.getNoticeGuid());
            boardAnnounce.update(boardNoticeUpdate.getNoticeTitle(),
                    boardNoticeUpdate.getNoticeContent());
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("noticeUpdate error", e);
        }
    }

    @Transactional
    public void deleteAnnounce(String noticeGuid) {
        try {
            BoardAnnounce boardAnnounce = boardAnnounceRepository.findBoardAnnounceByAnnouceGuid(noticeGuid);
            boardAnnounce.updateUseFlag(false);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("noticeDelete error", e);
        }
    }
}
