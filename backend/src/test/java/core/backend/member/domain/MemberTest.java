package core.backend.member.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class MemberTest {

    @Test
    @DisplayName("[Member, 생성] 회원")
    void createMember() {
        //given
        Member member = Member.builder()
                .email("test@gmail.com")
                .name("홍길동")
                .nickname("다섯 도마뱀")
                .password("sldfjweqwp232")
                .role(Role.USER).build();

        //when
        //then
        assertThat(member).isInstanceOf(Member.class);
        assertThat(member.getEmail()).isEqualTo("test@gmail.com");
        assertThat(member.getName()).isEqualTo("홍길동");
        assertThat(member.getNickname()).isEqualTo("다섯 도마뱀");
        assertThat(member.getPassword()).isEqualTo("sldfjweqwp232");
        assertThat(member.getRole()).isEqualTo(Role.USER);
    }
}