package project.movie.viewPage;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.client.RestTemplate;
import project.movie.store.domain.item.Item;
import project.movie.store.service.ItemService;

import java.util.List;
import java.util.Map;

@RequestMapping("/page")
@Controller
@RequiredArgsConstructor
public class ItemPageController {

    private final ItemService itemService;

    @GetMapping("/items")
    public String getItems(Model model){
        List<Item> items = itemService.itemFindAll();
        model.addAttribute("items",items);
        return "/store/items";
    }

    @GetMapping("/items/{id}")
    public String getItem(@PathVariable Integer id, Model model){
        Item item = itemService.itemFindByItemCode(id);

        model.addAttribute("item",item);
        return "/store/item-detail";
    }


}
