package com.project.board.main.api.service.announce;

import com.project.board.main.api.domain.announce.BoardMainAnnounce;
import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.announce.AnnounceInfo;
import com.project.board.main.api.dto.announce.AnnounceListPage;
import com.project.board.main.api.dto.announce.AnnounceRegist;
import com.project.board.main.api.dto.announce.AnnounceUpdate;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.repository.announce.BoardMainAnnounceRepository;
import com.project.board.main.api.repository.member.BoardMainMemberRepository;
import com.project.board.main.api.utils.jwt.JWTUtil;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnnounceService {
    private final JWTUtil jwtUtil;

    private final BoardMainMemberRepository boardMainMemberRepository;
    private final BoardMainAnnounceRepository boardMainAnnounceRepository;

    @Transactional
    public void announceRegist(AnnounceRegist announceRegist, String accessToken) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(jwtUtil.getUserUUID(accessToken));
        if (boardMainMember == null) throw new RuntimeException("존재하는 사용자가 없습니다.");
        if (boardMainMember.getMemberRole() != MemberRole.MASTER && boardMainMember.getMemberRole() != MemberRole.ADMIN) throw new RuntimeException("등록 할 수 있는 권한이 없습니다.");

        LocalDateTime nowDate = LocalDateTime.now();

        boardMainAnnounceRepository.save(BoardMainAnnounce.builder()
                .announceUUID(UUID.randomUUID())
                .announceTitle(announceRegist.getAnnounceTitle())
                .announceContent(announceRegist.getAnnounceContent())
                .boardMainMember(boardMainMember)
                .isActive(IsYesNo.YES)
                .insertDate(nowDate)
                .updateDate(nowDate)
                .build());
    }

    @Transactional
    public void announceUpdate(AnnounceUpdate announceUpdate, String accessToken) {
        if (checkAdmin(jwtUtil.getUserUUID(accessToken))) throw new RuntimeException("수정할 수 있는 권한이 없습니다.");

        BoardMainAnnounce boardMainAnnounce = boardMainAnnounceRepository.findBoardMainAnnounceByAnnounceUUID(announceUpdate.getAnnounceUUID());
        if (boardMainAnnounce == null) throw new RuntimeException("존재하는 공지사항이 없습니다.");

        boardMainAnnounce.update(
                announceUpdate.getAnnounceTitle(),
                announceUpdate.getAnnounceContent(),
                LocalDateTime.now()
        );
    }

    @Transactional
    public void announceDelete(UUID announceUUID, String accessToken) {
        if (checkAdmin(jwtUtil.getUserUUID(accessToken))) throw new RuntimeException("삭제 할 수 있는 권한이 없습니다.");

        BoardMainAnnounce boardMainAnnounce = boardMainAnnounceRepository.findBoardMainAnnounceByAnnounceUUID(announceUUID);
        if (boardMainAnnounce == null) throw new RuntimeException("존재하는 공지사항이 없습니다.");

        boardMainAnnounce.updateStatus(
                IsYesNo.NO,
                LocalDateTime.now()
        );
    }

    @Transactional
    public AnnounceInfo announceInfo(UUID announceUUID) {
        BoardMainAnnounce boardMainAnnounce = boardMainAnnounceRepository.findBoardMainAnnounceByAnnounceUUID(announceUUID);
        boardMainAnnounce.addViewCounter();

        return AnnounceInfo.create(boardMainAnnounce);
    }

    public AnnounceListPage announceListPage(String searchType, String searchValue) {
        return boardMainAnnounceRepository.findBoardMainAnnounceListPage(searchType, searchValue);
    }

    private boolean checkAdmin(UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID);
        return boardMainMember == null || (boardMainMember.getMemberRole() != MemberRole.MASTER && boardMainMember.getMemberRole() != MemberRole.ADMIN);
    }
}
