package com.project.board.main.api.service.board.development;

import com.project.board.main.api.domain.board.BoardMainDevelopment;
import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.board.development.DevelopmentInfo;
import com.project.board.main.api.dto.board.development.DevelopmentListPage;
import com.project.board.main.api.dto.board.development.DevelopmentRegist;
import com.project.board.main.api.dto.board.development.DevelopmentUpdate;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.repository.board.development.BoardMainDevelopmentRepository;
import com.project.board.main.api.repository.member.BoardMainMemberRepository;
import com.project.board.main.api.utils.jwt.JWTUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DevelopmentService {
    private final JWTUtil jwtUtil;

    private final BoardMainMemberRepository boardMainMemberRepository;
    private final BoardMainDevelopmentRepository boardMainDevelopmentRepository;

    @Transactional
    public void developmentRegist(DevelopmentRegist developmentRegist, String accessToken) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(jwtUtil.getUserUUID(accessToken));
        if (boardMainMember == null) throw new RuntimeException("존재하는 사용자가 없습니다.");

        boardMainDevelopmentRepository.save(BoardMainDevelopment.builder()
                .developmentTitle(developmentRegist.getDevelopmentTitle())
                .developmentContent(developmentRegist.getDevelopmentContent())
                .boardMainMember(boardMainMember)
                .build());
    }

    @Transactional
    public void developmentUpdate(DevelopmentUpdate developmentUpdate, String accessToken) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(jwtUtil.getUserUUID(accessToken));
        if (boardMainMember == null) throw new RuntimeException("존재하는 사용자가 없습니다.");

        BoardMainDevelopment boardMainDevelopment = boardMainDevelopmentRepository.findBoardMainDevelopmentByDevelopmentUUID(developmentUpdate.getDevelopmentUUID());
        if (boardMainDevelopment == null) throw new RuntimeException("존재하는 게시글이 없습니다.");
        if (!boardMainMember.getMemberUUID().equals(boardMainDevelopment.getBoardMainMember().getMemberUUID())) throw new RuntimeException("수정할 수 있는 권한이 없습니다.");

        boardMainDevelopment.update(developmentUpdate.getDevelopmentTitle(), developmentUpdate.getDevelopmentContent());
    }

    @Transactional
    public void developmentDelete(UUID developmentUUID, String accessToken) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(jwtUtil.getUserUUID(accessToken));
        if (boardMainMember == null) throw new RuntimeException("존재하는 사용자가 없습니다.");

        BoardMainDevelopment boardMainDevelopment = boardMainDevelopmentRepository.findBoardMainDevelopmentByDevelopmentUUID(developmentUUID);
        if (boardMainDevelopment == null) throw new RuntimeException("존재하는 게시글이 없습니다.");
        if (!boardMainMember.getMemberUUID().equals(boardMainDevelopment.getBoardMainMember().getMemberUUID()) && boardMainMember.getMemberRole().equals(MemberRole.MEMBER))
            throw new RuntimeException("삭제 할 수 있는 권한이 없습니다.");

        boardMainDevelopment.updateStatus(IsYesNo.NO);
    }

    @Transactional
    public DevelopmentInfo developmentInfo(UUID developmentUUID, String accessToken) {
        BoardMainDevelopment boardMainDevelopment = boardMainDevelopmentRepository.findBoardMainDevelopmentByDevelopmentUUID(developmentUUID);
        if (boardMainDevelopment == null) throw new RuntimeException("존재하는 게시글이 없습니다.");

        if (!boardMainDevelopment.getBoardMainMember().getMemberUUID().equals(jwtUtil.getUserUUID(accessToken))) boardMainDevelopment.addViewCounter();

        return DevelopmentInfo.create(boardMainDevelopment);
    }

    public DevelopmentListPage developmentListPage(String searchType, String searchValue) {
        return boardMainDevelopmentRepository.findDevelopmentListPage(searchType, searchValue);
    }
}
