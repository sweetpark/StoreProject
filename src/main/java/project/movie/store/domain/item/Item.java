package project.movie.store.domain.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name="item")
@Getter
@Setter
public class Item {

    @Id
    @Column(name ="item_code", nullable=false)
    private Integer itemCode;

    @Column(name = "item_type", nullable=false, length = 30)
    private String itemType; // 상품 종류

    @Column(name="item_name", nullable = false, length = 50)
    private String itemName; // 상품명

    @Column(name="item_detail", nullable = false, length = 100)
    private String itemDetail; // 상품 설명

    @Column(name="place", nullable = false, length = 500)
    private String place; // 사용처

    @Column(name="exp", nullable = false, length = 300)
    private String exp; // 유효기간

    @Column(name="price", nullable = false)
    private Integer price; // 판매금액

    @Column(name="sale_price")
    private Integer salePrice; // 할인금액

    @Column(name="item_image", nullable = false, length = 100)
    private String itemImage; // 상품 사진

    @Column(name="item_status", nullable = false)
    private Integer itemStatus; // 판매상태

    protected Item(){}

    public Item(Integer itemCode, String itemType, String itemName,
                String itemDetail, String place, String exp, Integer price, Integer salePrice,
                String itemImage, Integer itemStatus){
        this.itemCode = itemCode;
        this.itemType = itemType;
        this.itemName = itemName;
        this.itemDetail = itemDetail;
        this.place = place;
        this.exp = exp;
        this.price = price;
        this.salePrice = salePrice;
        this.itemImage = itemImage;
        this.itemStatus = itemStatus;
    }
}
