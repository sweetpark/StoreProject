package project.movie.store.repository.redis;

import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Service;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.store.domain.pay.Pay;

import java.time.Duration;

@Service
@Slf4j
@RequiredArgsConstructor
public class RedisService {
    private final RedisTemplate<String, String> redisTemplate;
    private final ObjectMapper objectMapper;

    public void saveRedis(Pay pay) {
        try{
            String payCode = pay.getPayCode();
            String payJson = objectMapper.writeValueAsString(pay);
            redisTemplate.opsForValue().set("pay:pending:"+payCode, payJson, Duration.ofMinutes(30));
            log.info("\n\n\n\n\n 결제 정보 : pay:pending:"+payCode +"\n\n\n\n\n");
        }catch(Exception e){
            throw new CustomApiException("결제 데이터 json 변환 저장 오류");
        }

    }

    public Pay getFromRedis(String payCode){
        try{
            String payJson = redisTemplate.opsForValue().get("pay:pending:"+payCode);
            return objectMapper.readValue(payJson, Pay.class);
        }catch (Exception e){
            throw new CustomApiException("결제 데이터 json 변환 조회 오류");
        }

    }

    public void deleteFromRedis(String payCode){
        if(redisTemplate.delete("pay:pending:"+payCode)){
            log.info("결제 전 데이터 삭제 : " + payCode);
        }else{
            log.warn("결제 전 데이터 삭제 실패 : " + payCode);
        }
    }
}
