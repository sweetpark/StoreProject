package project.movie.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.store.domain.cart.Cart;
import project.movie.store.domain.item.Item;

import java.util.List;
import java.util.Optional;

public interface CartRepository extends JpaRepository<Cart, Integer> {

    Optional<Cart> findByCartCode(Integer cartCode);
    List<Cart> findByMember_MemberId(String memberId);
    Cart findByItem_ItemCode(Integer itemCode);
    void deleteByItem_ItemCode(Integer itemCode);
    void deleteByCartCode(Integer cartCode);
}
