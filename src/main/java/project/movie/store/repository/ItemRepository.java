package project.movie.store.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import project.movie.store.domain.item.Item;

import java.util.Optional;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    Optional<Item> findByItemCode(int itemCode);
}
