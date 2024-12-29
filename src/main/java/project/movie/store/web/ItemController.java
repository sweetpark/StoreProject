package project.movie.store.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import project.movie.common.web.response.ResponseDto;
import project.movie.store.domain.item.Item;
import project.movie.store.dto.item.ItemRespDto;
import project.movie.store.service.ItemService;

import java.util.List;

@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
@Slf4j
public class ItemController {
    private final ItemService itemService;


    @GetMapping
    public ResponseEntity<?> getItemList(){
        List<Item> items = itemService.itemFindAll();
        List<ItemRespDto> itemRespDtos = itemService.convertToDtoList(items);
        return new ResponseEntity<>(new ResponseDto<>(1, "상점 품목 조회 성공", itemRespDtos), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getItem(@PathVariable Integer id){
        Item findItem = itemService.itemFindByItemCode(id);
        ItemRespDto itemRespDto = itemService.convertToDto(findItem);
        return new ResponseEntity<>(new ResponseDto<>(1, "품목 조회 성공", itemRespDto), HttpStatus.OK);
    }


}
