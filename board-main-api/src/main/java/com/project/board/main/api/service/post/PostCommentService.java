package com.project.board.main.api.service.post;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.domain.post.BoardMainPost;
import com.project.board.main.api.domain.post.BoardMainPostComment;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.dto.post.comment.PostCommentList;
import com.project.board.main.api.dto.post.comment.PostCommentRegist;
import com.project.board.main.api.dto.post.comment.PostCommentUpdate;
import com.project.board.main.api.repository.member.BoardMainMemberRepository;
import com.project.board.main.api.repository.post.BoardMainPostCommentRepository;
import com.project.board.main.api.repository.post.BoardMainPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostCommentService {
    private final BoardMainPostCommentRepository boardMainPostCommentRepository;
    private final BoardMainMemberRepository boardMainMemberRepository;
    private final BoardMainPostRepository boardMainPostRepository;

    @Transactional
    public void postCommentRegist(PostCommentRegist postCommentRegist, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        BoardMainPost boardMainPost = boardMainPostRepository.findBoardMainPostByPostUUID(postCommentRegist.getPostUUID())
                .orElseThrow(() -> new RuntimeException("존재하는 게시글이 없습니다."));

        boardMainPostCommentRepository.save(BoardMainPostComment.builder()
                .commentContent(postCommentRegist.getCommentContent())
                .boardMainMember(boardMainMember)
                .boardMainPost(boardMainPost)
                .isEncrypt(postCommentRegist.getIsEncrypt())
                .build());
    }

    @Transactional
    public void postCommentUpdate(PostCommentUpdate postCommentUpdate, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        BoardMainPostComment boardMainPostComment = boardMainPostCommentRepository.findBoardMainPostCommentByCommentUUID(postCommentUpdate.getCommentUUID())
                .orElseThrow(() -> new RuntimeException("존재하는 댓글이 없습니다."));

        if (!boardMainPostComment.getBoardMainMember().equals(boardMainMember)) throw new RuntimeException("수정할 수 있는 권한이 없습니다.");

        boardMainPostComment.update(postCommentUpdate.getCommentContent(), postCommentUpdate.getIsEncrypt());
    }

    @Transactional
    public void postCommentDelete(UUID commentUUID, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        BoardMainPostComment boardMainPostComment = boardMainPostCommentRepository.findBoardMainPostCommentByCommentUUID(commentUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 댓글이 없습니다."));

        if (!boardMainMember.equals(boardMainPostComment.getBoardMainMember()) && !MemberRole.isOverManager(boardMainMember.getMemberRole()))
            throw new RuntimeException("삭제할 수 있는 권한이 없습니다.");

        boardMainPostComment.updateStatus(IsYesNo.NO);
    }

    public List<PostCommentList> postCommentList(UUID postUUID) {
        BoardMainPost boardMainPost = boardMainPostRepository.findBoardMainPostByPostUUID(postUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 게시글이 없습니다."));
        return boardMainPostCommentRepository.findPostCommentListByBoardMainPost(boardMainPost);
    }
}
