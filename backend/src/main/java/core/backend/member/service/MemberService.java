package core.backend.member.service;

import core.backend.member.domain.Member;
import core.backend.member.dto.MemberCondition;
import core.backend.member.dto.MemberPasswordUpdateRequestDto;
import core.backend.member.dto.MemberUpdateRequestDto;
import core.backend.member.exception.EmailExistException;
import core.backend.member.exception.MemberNotFoundException;
import core.backend.member.exception.NickNameExistException;
import core.backend.member.exception.SignInFailedException;
import core.backend.member.repository.MemberConditionRepositoryImpl;
import core.backend.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class MemberService {

    private final MemberRepository memberRepository;
    private final MemberConditionRepositoryImpl memberConditionRepository;

    @Transactional
    public Long save(Member member) {
        isValidEmailAndNickname(member.getEmail(), member.getNickname());
        memberRepository.save(member);
        return member.getId();
    }

    @Transactional
    public Member update(Long id, MemberUpdateRequestDto dto) {
        Member member = findByIdOrThrow(id);
        member.update(dto);
        return member;
    }

    @Transactional
    public Long updatePassword(MemberPasswordUpdateRequestDto dto, PasswordEncoder encoder) {
        Member member = findByIdOrThrow(dto.getId());
        member.updatePassword(dto.getPassword(), encoder);
        return member.getId();
    }

    @Transactional
    public void deleteById(Long id) {
        memberRepository.deleteById(id);
    }

    public void isValidEmailAndNickname(String email, String nickname) {
        memberRepository.findByEmail(email)
                .ifPresent(e -> new EmailExistException());
        memberRepository.findByNickname(nickname)
                .ifPresent(e -> new NickNameExistException());
    }

    public Member findByCredentialOrThrow(String email, String password, PasswordEncoder encoder) {
        Member member = memberRepository.findByEmail(email)
                .orElseThrow(MemberNotFoundException::new);

        if (encoder.matches(password, member.getPassword())) {
            return member;
        }
        throw new SignInFailedException();
    }

    public Optional<Member> findByCondition(MemberCondition condition) {
        return memberConditionRepository.findByCondition(condition);
    }

    public Member findByIdOrThrow(Long id) {
        return memberRepository.findById(id)
                .orElseThrow(MemberNotFoundException::new);
    }

    public Page<Member> findAll(Pageable pageable) {
        return memberRepository.findAll(pageable);
    }
}
