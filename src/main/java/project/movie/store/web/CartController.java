package project.movie.store.web;


import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.movie.common.web.response.ResponseDto;
import project.movie.store.domain.cart.Cart;
import project.movie.store.dto.cart.*;
import project.movie.store.service.CartService;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@RequiredArgsConstructor
public class CartController {

    private final CartService cartService;

    @GetMapping
    public ResponseEntity<?> getAllCartItems(@AuthenticationPrincipal UserDetails userDetails){
        List<Cart> cartItems = cartService.getCartByMemberId(userDetails.getUsername());
        if (cartItems.isEmpty()){
            return new ResponseEntity<>(new ResponseDto<>(1,"장바구니가 비어있습니다.", null),HttpStatus.OK);
        }
        List<CartRespDto> cartRespDtos = cartService.convertToDtos(cartItems);
        return new ResponseEntity<>(new ResponseDto<>(1, "장바구니 조회 성공", cartRespDtos) ,HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> addItemsToCart(@RequestBody CartItemRequestDto item, @AuthenticationPrincipal UserDetails userDetils){
        cartService.cartSave(item, userDetils.getUsername());
        return new ResponseEntity<>(new ResponseDto<>(1,"장바구니에 상품이 추가되었습니다",item),HttpStatus.OK);
    }


    @PutMapping
    public ResponseEntity<?> updateCartItem(@RequestBody CartUpdateDto updateDto){
       return new ResponseEntity<>(cartService.updateCart(updateDto), HttpStatus.OK);
    }

    @DeleteMapping
    public ResponseEntity<?> deleteCartItem(@RequestBody CartDeleteDto deleteDto){
        cartService.deleteCart(deleteDto);
        return new ResponseEntity<>(new ResponseDto<>(1, "장바구니에서 삭제되었습니다",null), HttpStatus.OK);
    }
}
