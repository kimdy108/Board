package com.project.board.main.api.service.member;

import com.project.board.main.api.repository.member.BoardMainMemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final BoardMainMemberRepository boardMainMemberRepository;
}
