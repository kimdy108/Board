package com.project.board.main.api.domain.mongodb;

import jakarta.persistence.Id;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "board_main_post_viewer_info")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor(access = AccessLevel.PROTECTED)
public class BoardMainPostViewerInfo {
    @Id
    private String id;

    @Field("post_uuid")
    private String postUUID;

    @Field("member_uuid_list")
    private List<String> memberUUIDList;

    @Builder
    public BoardMainPostViewerInfo(String postUUID, List<String> memberUUIDList) {
        this.postUUID = postUUID;
        this.memberUUIDList = memberUUIDList;
    }
}
