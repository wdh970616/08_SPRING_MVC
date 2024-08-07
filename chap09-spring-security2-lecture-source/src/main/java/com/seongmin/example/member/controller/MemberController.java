package com.seongmin.example.member.controller;

import com.seongmin.example.member.model.dto.SignupDTO;
import com.seongmin.example.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/member")
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    // 회원가입 페이지로 이동
    @GetMapping("/register")
    public String register() {
        return "member/register";
    }

    @PostMapping("/register")
    public String register(SignupDTO signupDTO) {
        log.info("signup : {}", signupDTO);
        memberService.register(signupDTO);
        return "redirect:/";
    }
}
