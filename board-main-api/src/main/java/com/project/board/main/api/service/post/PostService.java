package com.project.board.main.api.service.post;

import com.project.board.main.api.domain.member.BoardMainMember;
import com.project.board.main.api.domain.post.BoardMainPost;
import com.project.board.main.api.dto.constant.common.IsYesNo;
import com.project.board.main.api.dto.constant.member.MemberRole;
import com.project.board.main.api.dto.constant.post.PostType;
import com.project.board.main.api.dto.post.post.PostInfo;
import com.project.board.main.api.dto.post.post.PostListPage;
import com.project.board.main.api.dto.post.post.PostRegist;
import com.project.board.main.api.dto.post.post.PostUpdate;
import com.project.board.main.api.repository.member.BoardMainMemberRepository;
import com.project.board.main.api.repository.post.BoardMainPostRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PostService {
    private final BoardMainMemberRepository boardMainMemberRepository;
    private final BoardMainPostRepository boardMainPostRepository;

    @Transactional
    public void postRegist(PostRegist postRegist, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        boardMainPostRepository.save(BoardMainPost.builder()
                .postTitle(postRegist.getPostTitle())
                .postContent(postRegist.getPostContent())
                .postType(postRegist.getPostType())
                .boardMainMember(boardMainMember)
                .build());
    }

    @Transactional
    public void postUpdate(PostUpdate postUpdate, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        BoardMainPost boardMainPost = boardMainPostRepository.findBoardMainPostByPostUUID(postUpdate.getPostUUID())
                .orElseThrow(() -> new RuntimeException("존재하는 게시글이 없습니다."));

        if (!boardMainMember.equals(boardMainPost.getBoardMainMember())) throw new RuntimeException("수정할 수 있는 권한이 없습니다.");

        boardMainPost.update(postUpdate.getPostTitle(), postUpdate.getPostContent());
    }

    @Transactional
    public void postDelete(UUID postUUID, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        BoardMainPost boardMainPost = boardMainPostRepository.findBoardMainPostByPostUUID(postUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 게시글이 없습니다."));

        if (!boardMainMember.equals(boardMainPost.getBoardMainMember()) &&
                !boardMainMember.getMemberRole().equals(MemberRole.MASTER) &&
                !boardMainMember.getMemberRole().equals(MemberRole.ADMIN))
            throw new RuntimeException("삭제할 수 있는 권한이 없습니다.");

        boardMainPost.updateStatus(IsYesNo.NO);
    }

    @Transactional
    public PostInfo postInfo(UUID postUUID, UUID memberUUID) {
        BoardMainMember boardMainMember = boardMainMemberRepository.findBoardMainMemberByMemberUUID(memberUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 사용자가 없습니다."));

        BoardMainPost boardMainPost = boardMainPostRepository.findBoardMainPostByPostUUID(postUUID)
                .orElseThrow(() -> new RuntimeException("존재하는 게시글이 없습니다."));

        if (!boardMainMember.getMemberUUID().equals(boardMainPost.getBoardMainMember().getMemberUUID())) boardMainPost.addViewCount();

        return PostInfo.create(boardMainPost);
    }

    public PostListPage postListPage(String searchType, String searchValue, PostType postType) {
        return boardMainPostRepository.findBoardMainPostListPage(searchType, searchValue, postType);
    }
}
