package com.project.board.main.api.service.announce;

import com.project.board.main.api.domain.announce.BoardMainAnnounce;
import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.domain.mongodb.BoardMainAnnounceViewerInfo;
import com.project.board.main.api.dto.announce.AnnounceInfo;
import com.project.board.main.api.dto.announce.AnnounceListPage;
import com.project.board.main.api.dto.announce.AnnounceRegist;
import com.project.board.main.api.dto.announce.AnnounceUpdate;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.repository.announce.BoardMainAnnounceRepository;
import com.project.board.main.api.repository.member.BoardMainMemberRepository;
import com.project.board.main.api.repository.mongodb.BoardMainAnnounceViewerInfoRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnnounceService {
    private final BoardMainMemberRepository boardMainMemberRepository;
    private final BoardMainAnnounceRepository boardMainAnnounceRepository;

    private final BoardMainAnnounceViewerInfoRepository boardMainAnnounceViewerInfoRepository;

    @Transactional
    public void announceRegist(AnnounceRegist announceRegist, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));
        if (boardMainMember.getMemberRole() != MemberRole.MASTER && boardMainMember.getMemberRole() != MemberRole.ADMIN) throw new RuntimeException("등록 할 수 있는 권한이 없습니다.");

        UUID uuid = UUID.randomUUID();

        boardMainAnnounceRepository.save(BoardMainAnnounce.builder()
                .announceUUID(uuid)
                .announceTitle(announceRegist.getAnnounceTitle())
                .announceContent(announceRegist.getAnnounceContent())
                .boardMainMember(boardMainMember)
                .build());

        boardMainAnnounceViewerInfoRepository.save(BoardMainAnnounceViewerInfo.builder()
                .announceUUID(uuid.toString())
                .memberUUIDList(List.of(boardMainMember.getMemberUUID().toString()))
                .build());
    }

    @Transactional
    public void announceUpdate(AnnounceUpdate announceUpdate, UUID memberUUID) {
        if (!checkAdmin(memberUUID)) throw new RuntimeException("수정할 수 있는 권한이 없습니다.");

        BoardMainAnnounce boardMainAnnounce = boardMainAnnounceRepository.findBoardMainAnnounceByAnnounceUUID(announceUpdate.getAnnounceUUID())
                        .orElseThrow(() -> new RuntimeException("존재하는 공지사항이 없습니다."));

        boardMainAnnounce.update(announceUpdate.getAnnounceTitle(), announceUpdate.getAnnounceContent());
    }

    @Transactional
    public void announceDelete(UUID announceUUID, UUID memberUUID) {
        if (!checkAdmin(memberUUID)) throw new RuntimeException("삭제 할 수 있는 권한이 없습니다.");

        BoardMainAnnounce boardMainAnnounce = boardMainAnnounceRepository.findBoardMainAnnounceByAnnounceUUID(announceUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 공지사항이 없습니다."));

        boardMainAnnounce.updateStatus(IsYesNo.NO);
    }

    @Transactional
    public AnnounceInfo announceInfo(UUID announceUUID, UUID memberUUID) {
        BoardMainAnnounce boardMainAnnounce = boardMainAnnounceRepository.findBoardMainAnnounceByAnnounceUUID(announceUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 공지사항이 없습니다."));

        BoardMainAnnounceViewerInfo boardMainAnnounceViewerInfo = boardMainAnnounceViewerInfoRepository.findBoardMainAnnounceViewerInfoByAnnounceUUID(boardMainAnnounce.getAnnounceUUID().toString());
        if (!boardMainAnnounceViewerInfo.getMemberUUIDList().contains(memberUUID.toString())) {
            boardMainAnnounce.addViewCount();

            boardMainAnnounceViewerInfo.getMemberUUIDList().add(memberUUID.toString());
            boardMainAnnounceViewerInfoRepository.save(boardMainAnnounceViewerInfo);
        }

        return AnnounceInfo.create(boardMainAnnounce);
    }

    public AnnounceListPage announceListPage(String searchType, String searchValue) {
        return boardMainAnnounceRepository.findBoardMainAnnounceListPage(searchType, searchValue);
    }

    private boolean checkAdmin(UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));
        return MemberRole.isOverAdmin(boardMainMember.getMemberRole());
    }
}
