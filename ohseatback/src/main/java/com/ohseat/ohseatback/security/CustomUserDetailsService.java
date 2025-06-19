package com.ohseat.ohseatback.security;

import com.ohseat.ohseatback.domain.User;
import com.ohseat.ohseatback.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

/**
 * 로그인 시 DB에서 사용자 조회 (UserDetailService 구현)
 */
@Service
@RequiredArgsConstructor
public class CustomUserDetailsService implements UserDetailsService {

    private final UserRepository userRepository;

//    public UserDetails loadUserById(Integer userId) {}

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email);
        if (user == null) throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        return new CustomUserDetails(user);
    }

    public CustomUserDetails loadUserById(Integer userId) throws UsernameNotFoundException {
        User user = userRepository.selectUserById(userId);
        if (user == null) throw new UsernameNotFoundException("사용자를 찾을 수 없습니다.");
        return new CustomUserDetails(user);
    }

}
