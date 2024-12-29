package project.movie.store.dto.pay;

import lombok.Builder;
import lombok.Getter;
import project.movie.store.domain.pay.Pay;
import project.movie.store.domain.pay.PayDetail;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@Builder
public class PayRespDto {
    private String payCode;
    private String impCode;
    private String memberId;
    private Integer payPrice;
    private LocalDateTime payDate;
    private LocalDateTime cancelDate;
    private Integer payStatus;
    private List<PayDetailRespDto> payDetails;

    public static PayRespDto from(Pay pay){
        return PayRespDto.builder()
                .payCode(pay.getPayCode())
                .impCode(pay.getImpCode())
                .memberId(pay.getMember().getMemberId())
                .payPrice(pay.getPayPrice())
                .payDate(pay.getPayDate())
                .cancelDate(pay.getCancelDate())
                .payStatus(pay.getPayStatus())
                .payDetails(pay.getPayDetails().stream()
                        .map(PayDetailRespDto::from)
                        .toList())
                .build();
    }
}
