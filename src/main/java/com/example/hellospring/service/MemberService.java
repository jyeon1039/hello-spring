package com.example.hellospring.service;

import com.example.hellospring.domain.Member;
import com.example.hellospring.repository.MemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Transactional
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public MemberService(MemberRepository memberRepository, PasswordEncoder passwordEncoder) {
        this.memberRepository = memberRepository;
        this.passwordEncoder = passwordEncoder;
    }

    // 회원가입
    public Long join(Member member) {
        validateDuplicateMember(member); // 중복 회원 검증
        String encodedPasswd = passwordEncoder.encode(member.getPasswd());
        member.setPasswd(encodedPasswd);
        memberRepository.save(member);
        return member.getId();
    }

    public Long login(Member member) {
        Optional<Member> res = memberRepository.findByEmail(member.getEmail());

        if (passwordEncoder.matches(member.getPasswd(), res.get().getPasswd())){
            return res.get().getId();
        }
        else
            return -1L;
    }

    public List<Member> findMembers() {
        return memberRepository.findAll();
    }

    public Optional<Member> findOne(Long memberId) {
        return memberRepository.findById(memberId);
    }

    private void validateDuplicateMember(Member member) {
        memberRepository.findByEmail(member.getEmail())
                .ifPresent(m -> {
                    throw new IllegalStateException("이미 존재하는 회원입니다.");
                });
    }
}
