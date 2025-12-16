package com.ohseat.ohseatback.security;

import com.ohseat.ohseatback.domain.user.entity.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

/**
 * 로그인한 사용자 정보 보관 (userId 꺼낼 수 있도록)
 */
public class CustomUserDetails implements UserDetails {

    private final User user;

    public CustomUserDetails(User user) {
        this.user = user;
    }

    public Integer getUserId() {
        return user.getUserId();
    }

    public String getRole() { return user.getRole(); }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        String role = user.getRole().toUpperCase();
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    @Override public String getPassword() { return user.getPassword(); }
    @Override public String getUsername() { return user.getEmail(); }
    @Override public boolean isAccountNonExpired() { return true; }
    @Override public boolean isAccountNonLocked() { return true; }
    @Override public boolean isCredentialsNonExpired() { return true; }
    @Override public boolean isEnabled() { return true; }

    public User getUser() {
        return user;
    }
}
