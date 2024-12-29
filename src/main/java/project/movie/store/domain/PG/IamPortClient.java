package project.movie.store.domain.PG;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.store.dto.PG.IamportResponseDto;
import project.movie.store.dto.PG.TokenResponseDto;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


@Component
@RequiredArgsConstructor
public class IamPortClient {

    @Value("${iamport.apiKey}")
    private String apiKey;
    @Value("${iamport.apiSecret}")
    private String apiSecret;
    @Value("${iamport.info}")
    private String impUid;

    private static final String BASE_URL = "https://api.iamport.kr/";
    private static final long TIMEOUT = 30000;  // 30초
    private final RestTemplate restTemplate;


    public String getImpPortUid(){
        return impUid;
    }

    @PostConstruct
    public void logIamportConfig() {
        System.out.println("API Key: " + apiKey);
        System.out.println("API Secret: " + apiSecret);
        System.out.println("IMP UID: " + impUid);
    }

    public IamportResponseDto paymentByImpUid(String impUid) throws JsonProcessingException {
        String accessToken = getAccessToken();

        String url = BASE_URL + "payments/" + impUid;

        ResponseEntity<IamportResponseDto> response = restTemplate.exchange(
                url,
                HttpMethod.GET,
                new HttpEntity<>(createHeaders(accessToken)),
                IamportResponseDto.class
        );


        return response.getBody();
    }


    public IamportResponseDto cancelPaymentByImpUid(String impUid, String reason) throws JsonProcessingException {

        String cancelUrl = BASE_URL + "payments/cancel";

        HttpHeaders headers = createHeaders(getAccessToken());
        headers.set("Content-Type", "application/json");

        Map<String, Object> cancelData = new HashMap<>();
        cancelData.put("imp_uid", impUid);
        cancelData.put("reason", reason);

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(cancelData, headers);

        try {
            // 응답을 IamportResponseDto로 처리 (단일 객체로 응답을 받을 때)
            ResponseEntity<IamportResponseDto> response = restTemplate.exchange(
                    cancelUrl,
                    HttpMethod.POST,
                    requestEntity,
                    IamportResponseDto.class
            );


            // 응답 상태가 OK이고, body가 null이 아니면 처리
            if (response.getStatusCode() == HttpStatus.OK && response.getBody() != null) {
                IamportResponseDto iamportResponseDto = response.getBody();

                // 응답 코드가 0인 경우만 처리
                if (iamportResponseDto.getCode() == 0) {
                    return iamportResponseDto;  // 성공적인 응답 반환
                } else {
                    throw new CustomApiException("결제 취소 실패: IamPort 승인 거절");
                }
            } else {
                throw new CustomApiException("결제 취소 실패: Iamport 응답 오류");
            }

        } catch (Exception e) {
            throw new CustomApiException("결제 취소 요청 중 오류가 발생했습니다: " + e.getMessage());
        }
    }


    private String getAccessToken() throws JsonProcessingException {
        String tokenUrl = BASE_URL + "users/getToken";

        HttpHeaders headers = new HttpHeaders();
        headers.set("Content-Type", "application/json");

        // Create the request DTO
        TokenRequestDto requestDto = new TokenRequestDto(apiKey, apiSecret);


        // Serialize the request body
        ObjectMapper objectMapper = new ObjectMapper();

        String body = objectMapper.writeValueAsString(requestDto);

        // Send the request
        ResponseEntity<TokenResponseDto> tokenResponse = restTemplate.exchange(
                tokenUrl,
                HttpMethod.POST,
                new HttpEntity<>(body, headers),
                TokenResponseDto.class
        );


        // Return the access token
        return tokenResponse.getBody().getResponse().getAccess_token();
    }

    private HttpHeaders createHeaders(String accessToken) {
        HttpHeaders headers = new HttpHeaders();

        if (accessToken != null) {
            headers.set("Authorization",accessToken);
        }
        return headers;
    }
}
