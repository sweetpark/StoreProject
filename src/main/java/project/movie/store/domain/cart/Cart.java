package project.movie.store.domain.cart;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.movie.member.domain.Member;
import project.movie.store.domain.item.Item;

import java.time.LocalDateTime;

@Entity
@Table(name="cart")
@Getter
@Setter
public class Cart {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cart_code", nullable = false)
    private Integer cartCode;

    @ManyToOne
    @JoinColumn(name="item_code", nullable = false)
    private Item item;

    @ManyToOne
    @JoinColumn(name="member_id", nullable = false)
    private Member member;

    @Column(name="cart_qty", nullable = false)
    private Integer cartQty;

    @Column(name="cart_status", nullable = false)
    private Integer cartStatus;

    @Column(name="cart_date", nullable = false)
    private LocalDateTime cartDate;

    protected Cart(){}
    public Cart(Item item, Member member, Integer cartQty, Integer cartStatus){
        this.item = item;
        this.member = member;
        this.cartQty = cartQty;
        this.cartStatus = cartStatus;
        this.cartDate = LocalDateTime.now();
    }



}
