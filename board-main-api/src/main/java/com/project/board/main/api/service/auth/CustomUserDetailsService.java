package com.project.board.main.api.service.auth;

import com.project.board.main.api.domain.member.BoardMember;
import com.project.board.main.api.dto.auth.AuthTokenBase;
import com.project.board.main.api.repository.member.BoardMemberRepository;
import com.project.board.main.api.utils.CustomUserDetails;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {
    private final BoardMemberRepository boardMemberRepository;

    @Override
    public UserDetails loadUserByUsername(String id) throws UsernameNotFoundException {
        BoardMember boardMember = boardMemberRepository.findBoardMemberByMemberIdAndUseFlag(id, true)
                .orElseThrow(() -> new RuntimeException("해당하는 유저가 없습니다."));

        AuthTokenBase authTokenBase = AuthTokenBase.create(boardMember.getMemberId(),
                boardMember.getMemberPassword(),
                boardMember.getMemberName(),
                boardMember.getMemberNickName(),
                boardMember.getMemberRole());
        return new CustomUserDetails(authTokenBase);
    }
}
