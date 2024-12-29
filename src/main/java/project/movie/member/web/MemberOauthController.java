package project.movie.member.web;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import project.movie.auth.oauth.OAuth2Service;
import project.movie.common.web.response.ResponseDto;

@Controller
@RequiredArgsConstructor
public class MemberOauthController {
    private final OAuth2Service oAuth2Service;

    @GetMapping("/naver/login")
    public String naverLogin() {
        return "naver-login";
    }

    @ResponseBody
    @GetMapping("/naver/callback")
    public ResponseEntity<?> callback(@RequestParam String code, HttpServletResponse response) {
        String jwtToken= oAuth2Service.signUp(code);
        oAuth2Service.addJwtToCookie(jwtToken, response);

        return new ResponseEntity<>(new ResponseDto<>(1, "로그인 성공" , null), HttpStatus.OK);
    }
}


