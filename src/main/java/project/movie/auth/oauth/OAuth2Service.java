package project.movie.auth.oauth;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;
import project.movie.auth.jwt.util.JWTOauthUtil;
import project.movie.member.domain.Member;
import project.movie.member.domain.MemberRole;
import project.movie.member.domain.MemberStatus;
import project.movie.member.repository.MemberRepository;


import java.util.UUID;

@RequiredArgsConstructor
@Service
public class OAuth2Service {
    private final JWTOauthUtil jwtUtil;

    @Value("${spring.security.oauth2.client.registration.naver.client-id}")
    private String NAVER_CLIENT_ID;
    @Value("${spring.security.oauth2.client.registration.naver.client-secret}")
    private String NAVER_CLIENT_SECRET;
    @Value("${spring.security.oauth2.client.registration.naver.redirect-uri}")
    private String NAVER_REDIRECT_URI;
    @Value("{spring.security.oauth2.client.provider.naver.user-info-uri}")
    private String NAVER_USER_INFO_URI;

    private final MemberRepository repository;

    public String signUp(String code){
        String accessToken = getAccessToken(code);
        String jwtToken = jwtUtil.generateJwt(getUserInfo(accessToken), 1000* 60 * 60L);
        return jwtToken;
    }

    private String getAccessToken(String code){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-type", "application/x-www-form-urlencoded; charset=utf-8");

        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("client_id", NAVER_CLIENT_ID);
        body.add("client_secret", NAVER_CLIENT_SECRET);
        body.add("redirect_uri", NAVER_REDIRECT_URI);
        body.add("code", code);

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(body,headers);
        RestTemplate rt = new RestTemplate();
        ResponseEntity<String> response;

        response = rt.exchange(
                "https://nid.naver.com/oauth2.0/token",
                HttpMethod.POST,
                request,
                String.class
        );

        String responseBody = response.getBody();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);
            return jsonNode.get("access_token").asText();
        }catch (Exception e){
            throw new RuntimeException(e);
        }

    }

    private Member getUserInfo(String accessToken){
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(headers);
        ResponseEntity<String> response;
        RestTemplate rt= new RestTemplate();

        response = rt.exchange(
                "https://openapi.naver.com/v1/nid/me",
                HttpMethod.POST,
                request,
                String.class
        );

        String responseBody = response.getBody();
        try{
            ObjectMapper objectMapper = new ObjectMapper();
            JsonNode jsonNode = objectMapper.readTree(responseBody);

            String id = provider(jsonNode.get("response").get("id").asText());

            Member member = repository.findByMemberId(id)
                    .orElseGet(() -> {
                        Member newMember = new Member(
                                id,
                                generatePassword(),
                                jsonNode.get("response").get("name").asText(),
                                jsonNode.get("response").get("email").asText(),
                                jsonNode.get("response").get("mobile").asText().replace("-", ""),
                                null, // other fields
                                null,
                                null,
                                null,
                                MemberRole.CUSTOMER,
                                MemberStatus.ACTIVE
                        );
                        return repository.save(newMember);
                    });

            return member;

        }catch(Exception e){
            throw new RuntimeException(e);
        }

    }

    private String generatePassword(){
        return UUID.randomUUID().toString();
    }

    private String provider(String id){
        return "Naver-"+id;
    }

    public void addJwtToCookie(String jwtToken, HttpServletResponse response) {
        Cookie cookie = new Cookie("token", jwtToken);
        cookie.setHttpOnly(true);  // JavaScript에서 접근 불가능하도록 설정
        cookie.setSecure(false);    // HTTPS에서만 전송되도록 설정
        cookie.setPath("/");       // 쿠키의 유효 경로 설정
        cookie.setMaxAge(60 * 60 * 24);  // 만료 시간 (예: 1일)
        response.addCookie(cookie);
    }
}
