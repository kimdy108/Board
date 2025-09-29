//package com.project.board.main.api.service.component;
//
//import com.project.board.main.api.domain.admin.BoardMainAdmin;
//import com.project.board.main.api.dto.component.CustomUserDetails;
//import com.project.board.main.api.repository.admin.BoardMainAdminRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.core.userdetails.UserDetailsService;
//import org.springframework.security.core.userdetails.UsernameNotFoundException;
//import org.springframework.stereotype.Service;
//
//@Service
//@RequiredArgsConstructor
//public class CustomUserDetailsService implements UserDetailsService {
//    private final BoardMainAdminRepository boardMainAdminRepository;
//
//    @Override
//    public UserDetails loadUserByUsername(String adminID) throws UsernameNotFoundException {
//        BoardMainAdmin boardMainAdmin = boardMainAdminRepository.findBoardMainAdminByAdminID(adminID);
//
//        if (boardMainAdmin != null) {
//            return new CustomUserDetails(boardMainAdmin);
//        }
//
//        return null;
//    }
//}
