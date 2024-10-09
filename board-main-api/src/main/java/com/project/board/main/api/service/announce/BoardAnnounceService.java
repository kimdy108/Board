package com.project.board.main.api.service.announce;

import com.project.board.main.api.dto.announce.BoardNotice;
import com.project.board.main.api.repository.announce.BoardAnnounceRepositoryImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BoardAnnounceService {
    private final BoardAnnounceRepositoryImpl boardAnnounceRepositoryImpl;

    @Transactional
    public List<BoardNotice> getAnnounceListAll() {
        return boardAnnounceRepositoryImpl.findBoardAnnounceListAll();
    }
}
