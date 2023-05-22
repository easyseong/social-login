package com.sociallogin.entity;

import com.sociallogin.constant.Role;
import com.sociallogin.dto.AddMember;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Table(name = "member")
@Getter
@Setter
public class Member {

    @Id
    @Column(name = "member_id")
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long memberNo;

    private String name;

    @Column(unique = true) // 이메일을 통해 유일하게 구분
    private String email;

    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    public static Member createMember(AddMember addMember, PasswordEncoder passwordEncoder) {
        Member member = new Member();
        member.setName(addMember.getName());
        member.setEmail(addMember.getEmail());
        String password = passwordEncoder.encode(addMember.getPassword());
        member.setPassword(password);
//        member.setRole(Role.USER);
        return member;
    }
}
