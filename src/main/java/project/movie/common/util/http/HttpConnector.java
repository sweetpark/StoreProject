package project.movie.common.util.http;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.HttpClient;
import org.springframework.http.*;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Component
@RequiredArgsConstructor
public class HttpConnector {

    private final HttpsUtility httpsUtility;

    /**
     * @param url 요청 API 주소
     * @return 검증 응답 문자열
     * @throws Exception
     */
    public ResponseEntity<String> sendHttpConnector(String url) throws Exception {
        log.info("sendHttpConnector START");
        log.info("sendHttpConnector url : {}", url);

        HttpClient httpClient = httpsUtility.httpClient();
        HttpComponentsClientHttpRequestFactory factory = httpsUtility.factory(httpClient);
        RestTemplate restTemplate = httpsUtility.restTemplate(factory);

        HttpHeaders headers = new HttpHeaders();
        headers.add("accept", "application/json");

        HttpEntity<String> requestEntity = new HttpEntity<>(headers);

        return restTemplate.exchange(url, HttpMethod.GET, requestEntity, String.class);
    }

    public boolean isError(HttpStatusCode statusCode) {
        if (statusCode.isError()) {
            log.debug("[상태 코드] MovieDataFetcherScheduler 실행 중 통신 장애 statusCode: {}", statusCode);
            return true;
        }
        return false;
    }

}
