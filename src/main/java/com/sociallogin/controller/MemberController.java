package com.sociallogin.controller;

import com.sociallogin.dto.AddMember;
import com.sociallogin.entity.Member;
import com.sociallogin.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@RequiredArgsConstructor
@Controller
@RequestMapping("/member")
public class MemberController {

    private final MemberService memberService;
    private final PasswordEncoder passwordEncoder;

    @GetMapping(value = "/new") // 회원 가입 페이지
    public String memberForm(Model model) {
        model.addAttribute("addMember", new AddMember());
        return "member/memberForm";
    }

    @PostMapping(value = "/new")
    public String memberForm(AddMember addMember) {
        Member member = Member.createMember(addMember, passwordEncoder);
        memberService.addMember(member);
        return "redirect:/";
    }

    @PostMapping("/new")
    public String newMember(@Valid AddMember addMember, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors()) {
            return "member/memberForm";
        }

        try {
            Member member = Member.createMember(addMember, passwordEncoder);
            memberService.addMember(member);
        } catch (IllegalStateException e) {
            model.addAttribute("errorMessage", e.getMessage());
            return "member/memberForm";
        }

        return "redirect:/";
    }

    //todo: 리프레시, 액세스 토큰
    //    @PostMapping("/auth/refreshtoken")
    //    public ResponseDto<?> refreshToken(@Valid @RequestBody final AuthenticationDomain.Request.RefreshToken refreshToken, HttpServletRequest request) {
    //
    //        return ResponseDto.of(authenticationService.refreshToken(refreshToken, request));
    //    }
}
