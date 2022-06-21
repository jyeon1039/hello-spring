package com.example.hellospring.controller;

import com.example.hellospring.domain.Member;
import com.example.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class MemberController {
    private final MemberService memberService;

    @Autowired
    public MemberController(MemberService memberService) {
        this.memberService = memberService;
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
}
