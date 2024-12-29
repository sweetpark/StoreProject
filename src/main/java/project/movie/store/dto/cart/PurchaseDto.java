package project.movie.store.dto.cart;

import lombok.Getter;
import lombok.Setter;
import project.movie.store.domain.cart.Cart;
import project.movie.store.domain.item.Item;

@Getter
@Setter
public class PurchaseDto {
    private Cart cart;
    private Integer itemQty;
    private Integer itemPerTotalPrice;

    public void calculateItemPerTotalPrice() {
        if (this.cart != null && this.itemQty != null) {
            this.itemPerTotalPrice = this.cart.getItem().getPrice() * this.itemQty;
        }
    }

}
