package com.project.board.main.api.dto.member;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class BoardMemberCheckChangePassword {
    private String memberId;
    private String memberGuid;

    public static BoardMemberCheckChangePassword create(String memberId,
                                                        String memberGuid) {
        BoardMemberCheckChangePassword boardMemberCheckChangePassword = new BoardMemberCheckChangePassword();
        boardMemberCheckChangePassword.memberId = memberId;
        boardMemberCheckChangePassword.memberGuid = memberGuid;
        return boardMemberCheckChangePassword;
    }
}
