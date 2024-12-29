package project.movie.auth.jwt.filter;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

public class CustomAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        response.setContentType("text/html; charset=UTF-8");
        response.getWriter().write(
                "<script>" +
                        "alert('로그인이 필요합니다');" +
                        "window.location.href='/';" +
                        "</script>"
        );
    }
}
