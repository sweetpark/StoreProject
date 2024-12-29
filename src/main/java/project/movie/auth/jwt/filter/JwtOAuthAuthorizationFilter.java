package project.movie.auth.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import project.movie.auth.jwt.dto.CustomUserDetails;
import project.movie.auth.jwt.util.JWTOauthUtil;

import project.movie.common.handler.exception.CustomApiException;
import project.movie.member.domain.Member;
import project.movie.member.repository.MemberRepository;

import java.io.IOException;

@Component
@RequiredArgsConstructor
public class JwtOAuthAuthorizationFilter extends OncePerRequestFilter {
    private final JWTOauthUtil jwtUtil;
    private final MemberRepository memberRepository;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String jwt = getJwtTokenFromCookie(request);
        if( jwt != null ){
            try {
                String userId = jwtUtil.getId(jwt);
                Member member = memberRepository.findByMemberId(userId)
                        .orElseThrow(() -> new CustomApiException("유저가 존재하지 않습니다"));

                CustomUserDetails customOAuth2User = new CustomUserDetails(member);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        customOAuth2User, null, customOAuth2User.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }else if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwt = authorizationHeader.substring(7);

            try {
                String userId = jwtUtil.getId(jwt);
                Member member = memberRepository.findByMemberId(userId)
                        .orElseThrow(()-> new CustomApiException("유저가 존재하지 않습니다"));

                CustomUserDetails customOAuth2User = new CustomUserDetails(member);

                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        customOAuth2User, null, customOAuth2User.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authentication);
            } catch (Exception e) {
                SecurityContextHolder.clearContext();
            }
        }
        chain.doFilter(request, response);
    }

    private String getJwtTokenFromCookie(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            for (Cookie cookie : cookies) {
                if ("token".equals(cookie.getName())) {
                    return cookie.getValue();
                }
            }
        }
        return null; // JWT 토큰이 없으면 null 반환
    }
}
