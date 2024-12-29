package project.movie.store.dto.cart;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.movie.member.domain.Member;
import project.movie.store.domain.cart.Cart;
import project.movie.store.domain.item.Item;

import java.time.LocalDateTime;

@Getter
@Setter
@Builder
public class CartRespDto {

    private Integer cartCode;
    private Item item;
    private String memberId;
    private Integer cartQty;
    private Integer cartStatus;
    private LocalDateTime cartDate;

    public CartRespDto(){}

    public CartRespDto(Integer cartCode, Item item, String memberId,
                       Integer cartQty, Integer cartStatus, LocalDateTime cartDate){
        this.cartCode = cartCode;
        this.item = item;
        this.memberId = memberId;
        this.cartQty = cartQty;
        this.cartStatus = cartStatus;
        this.cartDate = cartDate;
    }


    public static CartRespDto from(Cart cart){
        return CartRespDto.builder()
                .cartCode(cart.getCartCode())
                .item(cart.getItem())
                .memberId(cart.getMember().getMemberId())
                .cartQty(cart.getCartQty())
                .cartStatus(cart.getCartStatus())
                .cartDate(cart.getCartDate())
                .build();

    }
}
