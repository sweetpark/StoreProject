package project.movie.store.domain.coupon;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.movie.store.domain.item.Item;
import project.movie.store.domain.pay.Pay;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="coupon")
@Getter
@Setter
public class Coupon {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="cp_code", nullable = false)
    private Integer cpCode;

    @ManyToOne
    @JoinColumn(name="pay_code", nullable = false)
    private Pay pay;

    @ManyToOne
    @JoinColumn(name="item_code", nullable = false)
    private Item item;

    @Column(name="item_quantity", nullable = false)
    private Integer itemQuantity;

    @Column(name="cp_id")
    private String cpId;

    @Column(name="customer_id", nullable = false, length = 30)
    private String memberId;

    @Column(name="cp_date")
    private LocalDateTime cpDate;

    @Column(name="cp_status")
    private Integer cpStatus;

    public Coupon(){}

    public static String generateUUID(){
        return UUID.randomUUID().toString();
    }
}
