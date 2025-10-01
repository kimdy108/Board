package com.project.board.main.api.service.announce;

import com.project.board.main.api.domain.announce.BoardMainAnnounce;
import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.dto.announce.AnnounceListPage;
import com.project.board.main.api.dto.announce.AnnounceRegist;
import com.project.board.main.api.dto.announce.AnnounceUpdate;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.repository.announce.BoardMainAnnounceRepository;
import com.project.board.main.api.repository.member.BoardMainMemberRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AnnounceService {
    private final BoardMainMemberRepository boardMainMemberRepository;
    private final BoardMainAnnounceRepository boardMainAnnounceRepository;

    @Transactional
    public void announceRegist(AnnounceRegist announceRegist) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(announceRegist.getMemberUUID());
        if (boardMainMember == null) throw new RuntimeException("존재하는 사용자가 없습니다.");

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
    public void announceUpdate(AnnounceUpdate announceUpdate) {
        BoardMainAnnounce boardMainAnnounce = boardMainAnnounceRepository.findBoardMainAnnounceByAnnounceUUID(announceUpdate.getAnnounceUUID());
        if (boardMainAnnounce == null) throw new RuntimeException("존재하는 공지사항이 없습니다.");

        boardMainAnnounce.update(
                announceUpdate.getAnnounceTitle(),
                announceUpdate.getAnnounceContent(),
                LocalDateTime.now()
        );
    }

    @Transactional
    public void announceDelete(UUID announceUUID) {
        BoardMainAnnounce boardMainAnnounce = boardMainAnnounceRepository.findBoardMainAnnounceByAnnounceUUID(announceUUID);
        if (boardMainAnnounce == null) throw new RuntimeException("존재하는 공지사항이 없습니다.");

        // 삭제 시 IsActive 만 NO 로 변경
        boardMainAnnounceRepository.updateBoardMainAnnounceByActiveStatus(
                announceUUID,
                IsYesNo.NO,
                LocalDateTime.now()
        );
    }

    public AnnounceListPage announceListPage(String searchType, String searchValue) {
        return boardMainAnnounceRepository.findBoardMainAnnounceListPage(searchType, searchValue);
    }
}
