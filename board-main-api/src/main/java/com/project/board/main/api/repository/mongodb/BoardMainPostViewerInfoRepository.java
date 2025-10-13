package com.project.board.main.api.repository.mongodb;

import com.project.board.main.api.domain.mongodb.BoardMainPostViewerInfo;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface BoardMainPostViewerInfoRepository extends MongoRepository <BoardMainPostViewerInfo, String>{
    BoardMainPostViewerInfo findBoardMainPostViewerInfoByPostUUID(String postUUID);
}
