package project.movie.store.dto.pay;


import lombok.Builder;
import lombok.Getter;
import project.movie.store.domain.pay.PayDetail;


@Getter
@Builder
public class PayDetailRespDto {
    private Integer itemCode;
    private Integer quantity;

    public static PayDetailRespDto from (PayDetail payDetail){
        return PayDetailRespDto.builder()
                .itemCode(payDetail.getItem().getItemCode())
                .quantity(payDetail.getCartQty())
                .build();
    }
}
