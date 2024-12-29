package project.movie.viewPage;

import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.http.*;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;
import project.movie.common.web.response.ResponseDto;
import project.movie.store.domain.cart.Cart;
import project.movie.store.dto.cart.CartRespDto;
import project.movie.store.service.CartService;

import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
public class CartPageController {

    private final CartService cartService;


    @GetMapping("/page/cart")
    public String getCartPage(@AuthenticationPrincipal UserDetails userDetails, Model model) {
        List<Cart> cartItems = cartService.getCartByMemberId(userDetails.getUsername());
        if (cartItems.isEmpty()) {
            model.addAttribute("msg", "장바구니가 비어있습니다.");
            model.addAttribute("cartData", null);
        } else {
            model.addAttribute("msg", "장바구니 조회 성공");
            model.addAttribute("cartData", cartItems);
        }
        return "store/cart";
    }





}
