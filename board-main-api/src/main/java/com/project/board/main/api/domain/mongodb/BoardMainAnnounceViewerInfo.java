package com.project.board.main.api.domain.mongodb;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "board_main_announce_viewer_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardMainAnnounceViewerInfo {
    @Id
    private String id;

    @Field("announce_uuid")
    private String announceUUID;

    @Field("member_uuid_list")
    private List<String> memberUUIDList;

    @Builder
    public BoardMainAnnounceViewerInfo(String announceUUID, List<String> memberUUIDList) {
        this.announceUUID = announceUUID;
        this.memberUUIDList = memberUUIDList;
    }
}
