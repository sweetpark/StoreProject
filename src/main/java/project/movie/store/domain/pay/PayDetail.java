package project.movie.store.domain.pay;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.movie.store.domain.item.Item;

@Entity
@Table(name="paydetail")
@Getter
@Setter
public class PayDetail {

    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    @Column(name="payde_code", nullable = false)
    private Integer paydeCode;

    @JsonBackReference
    @ManyToOne
    @JoinColumn(name="pay_code", nullable = false)
    private Pay pay;

    @ManyToOne
    @JoinColumn(name="item_code", nullable = false)
    private Item item;

    @Column(name="cart_qty")
    private Integer cartQty;

    public PayDetail(){}
}
