//package com.project.board.main.api.dto.component;
//
//import com.project.board.main.api.domain.admin.BoardMainAdmin;
//import lombok.RequiredArgsConstructor;
//import org.springframework.security.core.GrantedAuthority;
//import org.springframework.security.core.userdetails.UserDetails;
//
//import java.util.ArrayList;
//import java.util.Collection;
//
//@RequiredArgsConstructor
//public class CustomUserDetails implements UserDetails {
//    private final BoardMainAdmin boardMainAdmin;
//
//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        Collection<GrantedAuthority> collection = new ArrayList<>();
//        collection.add((GrantedAuthority) () -> boardMainAdmin.getAdminRole().name());
//        return collection;
//    }
//
//    @Override
//    public String getPassword() {
//        return boardMainAdmin.getAdminPassword();
//    }
//
//    @Override
//    public String getUsername() {
//        return boardMainAdmin.getAdminName();
//    }
//
//    @Override
//    public boolean isAccountNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isAccountNonLocked() {
//        return true;
//    }
//
//    @Override
//    public boolean isCredentialsNonExpired() {
//        return true;
//    }
//
//    @Override
//    public boolean isEnabled() {
//        return true;
//    }
//}
