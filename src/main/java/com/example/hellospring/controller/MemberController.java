package com.example.hellospring.controller;

import com.example.hellospring.domain.Member;
import com.example.hellospring.jwt.JwtTokenProvider;
import com.example.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Controller
public class MemberController {
    private final MemberService memberService;
    private final JwtTokenProvider jwtTokenProvider;

    @Autowired
    public MemberController(MemberService memberService, JwtTokenProvider jwtTokenProvider) {
        this.memberService = memberService;
        this.jwtTokenProvider = jwtTokenProvider;
    }

    @GetMapping("/member")
    @ResponseBody
    public List<Member> list() {
        List<Member> members = memberService.findMembers();
        return members;
    }

    @PostMapping("/member")
    @ResponseBody
    public String create(@RequestBody MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "회원가입 완료"; //home 화면으로 보내기
    }

    @PostMapping("/login")
    @ResponseBody
    public Object login(@RequestBody MemberForm form) {
        Member member = new Member();
        member.setName(form.getName());

        Long userId = memberService.login(member);
        String token = jwtTokenProvider.makeJwtToken(userId);
        Map<String, Object> result = new HashMap<>();
        result.put("userId", userId);
        result.put("token", token);

        return result;
    }
}
