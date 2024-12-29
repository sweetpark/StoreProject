package project.movie.store.domain.cart;

import lombok.AllArgsConstructor;
import lombok.Getter;

public class CartStatus{
    public static final Integer CONTAIN = 1;
    public static final Integer NOCONTAIN = 2;
    public static final Integer DELETE = 3;
    public static final Integer PAID = 4;
}

