package core.backend.global.security;

import com.fasterxml.jackson.databind.ObjectMapper;
import core.backend.global.error.exception.ErrorCode;
import core.backend.member.service.MemberService;
import io.jsonwebtoken.ExpiredJwtException;
import io.jsonwebtoken.MalformedJwtException;
import io.jsonwebtoken.SignatureException;
import io.jsonwebtoken.UnsupportedJwtException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final TokenProvider tokenProvider;
    private final MemberService memberService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        try {
            String token = parseBearerToken(request);
            if (token != null && !token.equalsIgnoreCase("null")) {
                Long memberId = tokenProvider.validateAndGetMember(token);
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        memberService.findByIdOrThrow(memberId),
                        null,
                        AuthorityUtils.NO_AUTHORITIES);
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

                SecurityContext context = SecurityContextHolder.createEmptyContext();
                context.setAuthentication(authentication);
                SecurityContextHolder.setContext(context);
            }
        } catch (ExpiredJwtException e) {
            setErrorResponse(response, ErrorCode.EXPIRED_TOKEN);
        } catch (UnsupportedJwtException | IllegalArgumentException e) {
            setErrorResponse(response, ErrorCode.INVALID_TOKEN);
        } catch (SecurityException | MalformedJwtException | SignatureException e) {
            setErrorResponse(response, ErrorCode.WRONG_TOKEN);
        } catch (Exception e) {
            e.printStackTrace();
        }
        filterChain.doFilter(request, response);
    }

    private void setErrorResponse(HttpServletResponse response, ErrorCode errorCode) {
        response.setStatus(errorCode.getHttpStatus().value());
        response.setContentType("application/json; charset=UTF-8");

        try {
            PrintWriter writer = response.getWriter();
            writer.write(new ObjectMapper()
                    .writeValueAsString(
                            getMapWithResponseMsg(new HashMap(), errorCode)));
            writer.flush();
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private HashMap getMapWithResponseMsg(HashMap map, ErrorCode errorCode) {
        map.put("status", errorCode.getHttpStatus().value());
        map.put("error", errorCode.getHttpStatus().name());
        map.put("code", errorCode.name());
        map.put("message", errorCode.getMessage());
        return map;
    }

    private String parseBearerToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");

        if (StringUtils.hasText(bearerToken) && bearerToken.startsWith("Bearer ")) {
            return bearerToken.substring(7);
        }
        return null;
    }
}
