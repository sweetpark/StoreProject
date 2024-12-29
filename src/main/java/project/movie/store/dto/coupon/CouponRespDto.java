package project.movie.store.dto.coupon;

import lombok.Builder;
import project.movie.store.domain.coupon.Coupon;
import java.time.LocalDateTime;

@Builder
public class CouponRespDto {
    private String payCode;
    private Integer itemCode;
    private String cpId; // UUID 바코드 값
    private String memberId;
    private LocalDateTime cpDate;
    private Integer cpStatus;


    public static CouponRespDto from(Coupon coupon){

        return CouponRespDto.builder()
                .payCode(coupon.getPay().getPayCode())
                .itemCode(coupon.getItem().getItemCode())
                .cpId(coupon.getCpId())
                .memberId(coupon.getMemberId())
                .cpDate(coupon.getCpDate())
                .cpStatus(coupon.getCpStatus())
                .build();
    }
}
