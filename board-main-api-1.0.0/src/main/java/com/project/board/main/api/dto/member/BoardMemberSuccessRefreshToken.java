package com.project.board.main.api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardMemberSuccessRefreshToken {
    private String accessToken;
    private boolean outPut;

    public static BoardMemberSuccessRefreshToken create(String accessToken) {
        BoardMemberSuccessRefreshToken boardMemberSuccessRefreshToken = new BoardMemberSuccessRefreshToken();
        boardMemberSuccessRefreshToken.setAccessToken(accessToken);
        boardMemberSuccessRefreshToken.setOutPut(true);
        return boardMemberSuccessRefreshToken;
    }
}
