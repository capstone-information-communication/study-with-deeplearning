package core.backend.member;

import core.backend.global.dto.DataResponse;
import core.backend.global.security.TokenProvider;
import core.backend.member.domain.Member;
import core.backend.member.dto.MemberResponseDto;
import core.backend.member.dto.MemberSignInRequestDto;
import core.backend.member.dto.MemberSignInResponseDto;
import core.backend.member.dto.MemberSignUpRequestDto;
import core.backend.member.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1")
public class MemberController {

    private final MemberService memberService;
    private final TokenProvider tokenProvider;
    private final PasswordEncoder encoder = new BCryptPasswordEncoder();

    @GetMapping("/member")
    public ResponseEntity<MemberResponseDto> findSignInMemberV1(
            @AuthenticationPrincipal Member member) {
        return ResponseEntity.ok(
                new MemberResponseDto(memberService.findByIdOrThrow(member.getId()))
        );
    }

    @GetMapping("/members")
    public ResponseEntity<DataResponse> findAllV1(
            @PageableDefault Pageable pageable) {
        List<MemberResponseDto> result = memberService.findAll(pageable)
                .stream()
                .map(MemberResponseDto::new)
                .collect(Collectors.toList());

        return ResponseEntity.ok(
                DataResponse.builder().data(result).count(result.size()).build());
    }

    @PostMapping("/sign-in")
    public ResponseEntity<MemberSignInResponseDto> signInV1(
            @RequestBody MemberSignInRequestDto dto) {
        Member member = memberService.findByCredentialOrThrow(
                dto.getEmail(),
                dto.getPassword(),
                encoder);

        String token = tokenProvider.createToken(member);
        return ResponseEntity.ok(
                new MemberSignInResponseDto(token));
    }

    @PostMapping("/sign-up")
    public ResponseEntity<MemberResponseDto> signUpV1(
            @RequestBody MemberSignUpRequestDto dto) {
        memberService.isValidEmailAndNickname(dto.getEmail(), dto.getNickname());
        Long id = memberService.save(dto.toEntity(encoder.encode(dto.getPassword())));
        return ResponseEntity.ok(
                new MemberResponseDto(memberService.findByIdOrThrow(id)));
    }
}
