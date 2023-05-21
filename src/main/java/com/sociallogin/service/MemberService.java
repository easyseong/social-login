package com.sociallogin.service;

import com.sociallogin.entity.Member;
import com.sociallogin.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
@Transactional
public class MemberService {

    private final MemberRepository memberRepository;

    public Member addMember(Member member) {
        validateDuplicateMember(member);
        return memberRepository.save(member);
    }

    private void validateDuplicateMember(Member member) {
        if (memberRepository.existsByEmail(member.getEmail())) {
            //todo: ApplicationException.class 추가
            throw new IllegalStateException("이미 사용된 이메일 입니다.");
        }
    }


}
