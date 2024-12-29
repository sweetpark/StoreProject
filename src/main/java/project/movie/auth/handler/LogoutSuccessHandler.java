package project.movie.auth.handler;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import java.io.IOException;

public class LogoutSuccessHandler implements org.springframework.security.web.authentication.logout.LogoutSuccessHandler {
    private Logger logger = LoggerFactory.getLogger(this.getClass());

    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response, Authentication authentication) throws IOException, ServletException {
        // 로그아웃할 때 시큐리티 컨텍스트를 해제한다.
        logger.info("CsutomLogoutSuccessHandler() 메서드를 실행하였습니다");
        // 세션을 삭제한다.
        //request.getSession().invalidate();
        SecurityContextHolder.clearContext();
        // 로그아웃 후에 로그인 페이지로 이동한다.
        response.sendRedirect("/");
    }
}
