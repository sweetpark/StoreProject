package project.movie.auth.jwt.filter;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.InternalAuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.movie.auth.jwt.dto.CustomUserDetails;
import project.movie.auth.jwt.util.JWTUtil;

import java.io.IOException;
import java.util.Collection;
import java.util.Iterator;

public class JwtAuthenticationFilter extends UsernamePasswordAuthenticationFilter {
    private final AuthenticationManager authenticationManager;
    private final JWTUtil jwtUtil;

    public JwtAuthenticationFilter(AuthenticationManager authenticationManager, JWTUtil jwtUtil) {
        setFilterProcessesUrl("/login");
        this.authenticationManager = authenticationManager;
        this.jwtUtil = jwtUtil;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        System.out.println("LoginFilter 클래스의 attemptAuthentication 메서드 동작함");
        String username = obtainUsername(request);
        String password = obtainPassword(request);

        System.out.println("===================");
        System.out.println(username);
        System.out.println(password);

        UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(username, password, null);
        System.out.println(authToken);

        return authenticationManager.authenticate(authToken);
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authentication) throws IOException, ServletException {
        try {
            // UserDetails
            CustomUserDetails customUserDetails = (CustomUserDetails) authentication.getPrincipal();

            String username = customUserDetails.getUsername();

            Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
            Iterator<? extends GrantedAuthority> iterator = authorities.iterator();
            GrantedAuthority auth = iterator.next();

            // 권한 획득
            String role = auth.getAuthority();

            // 토큰 생성
            String token = jwtUtil.createJwt(username, role, 1000 * 60 * 60L);

            response.addCookie(createCookie("Authorization", token));
            response.addHeader("Authorization", "Bearer " + token);
        } catch (InternalAuthenticationServiceException e) {
            System.out.println("successfulAuthentication 메서드 에러 발생 : " + e.getMessage());
        }
    }

    @Override
    protected void unsuccessfulAuthentication(HttpServletRequest request, HttpServletResponse response, AuthenticationException failed) throws IOException, ServletException {
        String message = failed.getMessage();
        //로그인 실패시 401 응답 코드 반환
        response.setStatus(401);
        System.out.println("fail authentication");
    }

    private Cookie createCookie(String key, String value) {

        Cookie cookie = new Cookie(key, value);
        cookie.setMaxAge(60*60*60);
        cookie.setPath("/");
        cookie.setHttpOnly(true);

        return cookie;
    }
}
