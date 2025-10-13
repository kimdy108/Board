package com.project.board.main.api.repository.mongodb;

import com.project.board.main.api.domain.mongodb.BoardMainAnnounceViewerInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMainAnnounceViewerInfoRepository extends MongoRepository<BoardMainAnnounceViewerInfo,String> {
    BoardMainAnnounceViewerInfo findBoardMainAnnounceViewerInfoByAnnounceUUID(String announceUUID);
}
