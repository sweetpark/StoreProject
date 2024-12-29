package project.movie.viewPage;


import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.store.domain.cart.Cart;
import project.movie.store.domain.item.Item;
import project.movie.store.domain.pay.Pay;
import project.movie.store.domain.pay.PayDetail;
import project.movie.store.dto.pay.PayRespDto;
import project.movie.store.service.CartService;
import project.movie.store.service.ItemService;
import project.movie.store.service.PayService;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;


@Controller
@RequiredArgsConstructor
@RequestMapping("/page/pay")
public class PayPageController {

    private final CartService cartService;
    private final ItemService itemService;
    private final PayService payService;

    @GetMapping
    public String showPaymentAll(@AuthenticationPrincipal UserDetails userDetails, Model model){
        List<Pay> pays = payService.getPayInfoAllByMember(userDetails.getUsername());
        List<PayRespDto> payRespDtos = payService.convertToDtos(pays);

        model.addAttribute("pays", payRespDtos);
        return "store/pays";
    }


    @GetMapping("/{payCode}")
    public String showPayment(@PathVariable String payCode, @AuthenticationPrincipal UserDetails userDetails, Model model){

        Pay payRespDto = payService.getFindByPayCode(payCode);
        System.out.println(payRespDto.getPayDetails());
        List<PayDetail>payDetails =  payRespDto.getPayDetails();

        List<Item> items = new ArrayList<>();
        for (PayDetail payDetail : payDetails) {
            items.add(itemService.itemFindByItemCode(payDetail.getItem().getItemCode()));
        }

        model.addAttribute("pay", payRespDto);
        model.addAttribute("payDetails", payDetails);
        model.addAttribute("items", items);
        return "store/pay-detail";
    }


    @PostMapping
    public String showPaymentPage(@RequestParam("selectedItems") String selectedItems,
                                  Model model) {
        // selectedItems에서 마지막 [] 안의 부분만 추출
        String cartCodeJson = selectedItems.substring(selectedItems.indexOf("[") + 1, selectedItems.lastIndexOf("]"));
        String[] cartCodes = cartCodeJson.replace("\"", "").split(",");

        List<Cart> carts = new ArrayList<>();
        Integer calculatedTotalPrice = 0;


        for (String cartCode : cartCodes) {
            System.out.println(cartCode);
            Cart cart = cartService.findByCartCode(Integer.parseInt(cartCode));
            if (cart != null) {
                carts.add(cart);
                calculatedTotalPrice += (cart.getItem().getPrice() - cart.getItem().getSalePrice()) * cart.getCartQty();
            }
        }

        List<Integer> cartCodes_ = carts.stream()
                .map(Cart::getCartCode)
                .collect(Collectors.toList());

//        calculatedTotalPrice = 100;

        model.addAttribute("cartItems",carts);
        model.addAttribute("totalPrice", calculatedTotalPrice);
        model.addAttribute("cartCodes", cartCodes_);

        return "store/before-pay";  // payment.html page
    }

    @PostMapping("/direct")
    public String directPaymentPage(@RequestParam("itemCode") Integer itemCode,
                                    @RequestParam("purchase-quantity") Integer itemQty,
                                    Model model){

        Item item = itemService.itemFindByItemCode(itemCode);
        Integer totalPrice = (item.getPrice() - item.getSalePrice())* itemQty;

//        totalPrice = 100;

        model.addAttribute("item", item);
        model.addAttribute("quantity", itemQty);
        model.addAttribute("totalPrice", totalPrice);
        return "store/direct-before-pay";
    }
}
