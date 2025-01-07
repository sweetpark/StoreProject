package project.movie.store.service;

import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;
import project.movie.store.domain.item.*;

import java.io.File;
import java.util.List;

@RequiredArgsConstructor
@Component
public class ItemDataInitializer implements ApplicationRunner {
    private final ItemService itemService;


    @Override
    public void run(ApplicationArguments args){
        File popcornFile = new File("/images/popcorn_640.jpg");
        File ticketFile = new File("/images/ticket_640.png");
        File comboFile = new File("/images/popcorn-ticket_640.png");

        itemService.deleteAll();
        List<Item> existingItems = itemService.itemFindAll();

        //itemCode값은 정적인 이미지 1개당 적용 (기본키이므로, 중복안되게 설정)
        if (existingItems.isEmpty()){
            Item popcornItem = new Item(
                    1, ItemType.FOOD,
                    "팝콘","팝콘",
                    ItemPlaceType.NATIONWIDE, ExpConfig.dateSet(120),
                    8000,7900,popcornFile.getPath(),
                    ItemStatus.PROGRESS);

            Item popcornItem2 = new Item(
                    2, ItemType.FOOD,
                    "팝콘 2개","팝콘",
                    ItemPlaceType.NATIONWIDE, ExpConfig.dateSet(120),
                    16000,2000,popcornFile.getPath(),
                    ItemStatus.PROGRESS);

            Item popcornItem3 = new Item(
                    3, ItemType.FOOD,
                    "팝콘 3개","팝콘",
                    ItemPlaceType.NATIONWIDE, ExpConfig.dateSet(120),
                    24000,3000,popcornFile.getPath(),
                    ItemStatus.PROGRESS);

            Item ticketItem = new Item(
                    4, ItemType.TICKET,
                    "영화티켓","영화 티켓 쿠폰",
                    ItemPlaceType.NATIONWIDE, ExpConfig.dateSet(120),
                    15000,1000,ticketFile.getPath(),
                    ItemStatus.PROGRESS);

            Item ticketItem2 = new Item(
                    5, ItemType.TICKET,
                    "영화티켓 2장","영화 티켓 쿠폰",
                    ItemPlaceType.NATIONWIDE, ExpConfig.dateSet(120),
                    30000,2000,ticketFile.getPath(),
                    ItemStatus.PROGRESS);

            Item ticketItem3 = new Item(
                    6, ItemType.TICKET,
                    "영화티켓 패밀리 세트 (3명)","영화 티켓 쿠폰",
                    ItemPlaceType.NATIONWIDE, ExpConfig.dateSet(120),
                    45000,3000,ticketFile.getPath(),
                    ItemStatus.PROGRESS);

            Item comboItem = new Item(
                    7, ItemType.COMBO, "티켓 + 팝콘",
                    "영화 티켓 2장 + 팝콘 1개 콤포구성",
                    ItemPlaceType.NATIONWIDE, ExpConfig.dateSet(120),
                    38000,3000,comboFile.getPath(),
                    ItemStatus.PROGRESS);

            Item comboItem2 = new Item(
                    8, ItemType.COMBO, "티켓 + 팝콘",
                    "영화 티켓 1장 + 팝콘 1개 콤포구성",
                    ItemPlaceType.NATIONWIDE, ExpConfig.dateSet(120),
                    23000,3000,comboFile.getPath(),
                    ItemStatus.PROGRESS);

            itemService.itemSave(popcornItem);
            itemService.itemSave(popcornItem2);
            itemService.itemSave(popcornItem3);
            itemService.itemSave(ticketItem);
            itemService.itemSave(ticketItem2);
            itemService.itemSave(ticketItem3);
            itemService.itemSave(comboItem);
            itemService.itemSave(comboItem2);
        }
    }
}
