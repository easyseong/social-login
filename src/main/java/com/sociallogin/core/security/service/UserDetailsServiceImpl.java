package com.sociallogin.core.security.service;

import com.sociallogin.entity.Member;
import com.sociallogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


// UserDetailsService (interface): 데이터베이스에서 회원 정보를 가져오는 역할을 담당(로그인 기능 구현)
@Service
@RequiredArgsConstructor
public class UserDetailsServiceImpl implements UserDetailsService {

    private final MemberRepository memberRepository;


    @Override
    @Transactional(readOnly = true)
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Member member = memberRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException("User Not Found with email: " + email));

        return User.builder() // User.class : UserDetails 인터페이스(회원의 정보를 담기위해 사용하는 인터페이스)를 구현하고 있는 시큐리티에서 제공하는 클래스
                .username(member.getEmail())
                .password(member.getPassword())
                .build();
    }

}
