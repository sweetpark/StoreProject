package project.movie.store.domain.pay;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import project.movie.member.domain.Member;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Entity
@Table(name="pay")
@Getter
@Setter
public class Pay {

    @Id
    @Column(name="pay_code", nullable = false, length = 100)
    private String payCode;

    @Column(name="imp_code")
    private String impCode;

    @ManyToOne
    @JoinColumn(name="customer_id", nullable = false)
    private Member member;

    @Column(name="pay_type", nullable = false, length = 50)
    private String payType;

    @Column(name="pay_price", nullable = false)
    private Integer payPrice;

    @Column(name="pay_date", nullable = false)
    private LocalDateTime payDate;

    @Column(name="cancel_date")
    private LocalDateTime cancelDate;

    @Column(name="pay_status", nullable = false)
    private Integer payStatus;

    @JsonManagedReference
    @OneToMany(mappedBy = "pay", cascade= CascadeType.ALL)
    private List<PayDetail> payDetails;

    public Pay(){};

    public void generatePayCode(){
        String payCode = UUID.randomUUID().toString();
        this.payCode = payCode;
    }
}
