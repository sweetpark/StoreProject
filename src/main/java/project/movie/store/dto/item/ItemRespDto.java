package project.movie.store.dto.item;


import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import project.movie.store.domain.item.Item;

@Getter
@Setter
@Builder
public class ItemRespDto {
    private Integer itemCode;
    private String itemType; // 상품 종류
    private String itemName; // 상품명
    private String itemDetail; // 상품 설명
    private String place; // 사용처
    private String exp; // 유효기간
    private Integer price; // 판매금액
    private Integer salePrice; // 할인금액
    private String itemImage; // 상품 사진
    private Integer itemStatus; // 판매상태

    public ItemRespDto(){}

    public ItemRespDto(Integer itemCode, String itemType, String itemName, String itemDetail,
                       String place, String exp, Integer price, Integer salePrice,
                       String itemImage, Integer itemStatus) {
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

    public static ItemRespDto from(Item item){
        return ItemRespDto.builder()
                .itemCode(item.getItemCode())
                .itemType(item.getItemType())
                .itemName(item.getItemName())
                .itemDetail(item.getItemDetail())
                .place(item.getPlace())
                .exp(item.getExp())
                .price(item.getPrice())
                .salePrice(item.getSalePrice())
                .itemImage(item.getItemImage())
                .itemStatus(item.getItemStatus())
                .build();
    }
}
