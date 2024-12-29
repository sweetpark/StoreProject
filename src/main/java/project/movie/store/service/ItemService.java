package project.movie.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.store.domain.item.Item;
import project.movie.store.dto.item.ItemRespDto;
import project.movie.store.repository.ItemRepository;

import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository repository;

    @Transactional
    public void itemSave(Item item){
        repository.save(item);
    }

    public List<Item> itemFindAll(){
        return repository.findAll();
    }

    public Item itemFindByItemCode(int id){
        return repository.findByItemCode(id)
                .orElseThrow(() -> new CustomApiException( "존재하지 않는 상품입니다."));
    }

    public List<ItemRespDto> convertToDtoList(List<Item> items){
        return items.stream()
                .map(this::convertToDto)
                .toList();
    }

    public ItemRespDto convertToDto(Item item){
        return ItemRespDto.from(item);
    }

    public void deleteAll(){
        repository.deleteAll();
    }

}
