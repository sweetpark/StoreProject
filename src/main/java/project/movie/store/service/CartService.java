package project.movie.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.common.web.response.ResponseDto;
import project.movie.member.domain.Member;
import project.movie.member.service.MemberService;
import project.movie.store.domain.cart.Cart;
import project.movie.store.domain.cart.CartStatus;
import project.movie.store.domain.item.Item;
import project.movie.store.domain.pay.Pay;
import project.movie.store.domain.pay.PayDetail;
import project.movie.store.dto.cart.CartDeleteDto;
import project.movie.store.dto.cart.CartItemRequestDto;
import project.movie.store.dto.cart.CartRespDto;
import project.movie.store.dto.cart.CartUpdateDto;
import project.movie.store.repository.CartRepository;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;


@Service
@RequiredArgsConstructor
@Slf4j
public class CartService {

    private final CartRepository repository;
    private final ItemService itemService;
    private final MemberService memberService;

    //장바구니 담기
    @Transactional
    public void cartSave(CartItemRequestDto item, String memberId){

        Item findItem = itemService.itemFindByItemCode(item.getItemCode());
        Cart cartItem = repository.findByItem_ItemCode(item.getItemCode());
        if ( cartItem != null && findItem.equals(cartItem.getItem())){
            cartItem.setCartQty(cartItem.getCartQty() + item.getQuantity());
        }else{
            Member findMember = memberService.getByMemberId(memberId);
            Cart newCart = new Cart(findItem, findMember, item.getQuantity(), CartStatus.CONTAIN);

            repository.save(newCart);
        }

    }


    //고객 당 장바구니 조회하기
    public List<Cart> getCartByMemberId(String memberId){
        List<Cart> carts = repository.findByMember_MemberId(memberId);
        for (Cart cart : carts) {
            //방어코드
            if ( cart.getCartStatus().equals(CartStatus.PAID) || cart.getCartStatus().equals(CartStatus.DELETE)){
                repository.deleteById(cart.getCartCode());
            }
        }
        return carts;
    }


    //장바구니에서 삭제하기
    public void deleteItem(Integer itemCode){
        repository.deleteByItem_ItemCode(itemCode);
    }

    public Cart findByCartCode(Integer cartCode){
        return repository.findByCartCode(cartCode)
                .orElseThrow(() -> new CustomApiException("장바구니가 존재하지 않습니다."));
    }

    @Transactional
    public ResponseDto<?> updateCart(CartUpdateDto uCart){
        if ( uCart.getCartQty() <= 0 || uCart.getCartStatus().equals(CartStatus.DELETE) || uCart.getCartStatus().equals(CartStatus.PAID)){
            deleteItem(uCart.getItemCode());
            return new ResponseDto<>(1, "장바구니에서 삭제되었습니다", uCart);
        }else{
            Cart cart = findByCartCode(Integer.parseInt(uCart.getCartCode()));
            cart.setCartQty(uCart.getCartQty());
            cart.setCartDate(LocalDateTime.now());
            CartRespDto cartRespDto = convertToDto(cart);
            return new ResponseDto<>(1, "장바구니가 성공적으로 업데이트 되었습니다", cartRespDto);
        }
    }

    @Transactional
    public void paidCart(Pay pay){

        List<Cart> cartsToDelete = new ArrayList<>();
        for (PayDetail payDetail : pay.getPayDetails()) {
            Cart cart = repository.findByItem_ItemCode(payDetail.getItem().getItemCode());
            if (cart != null) {
                cartsToDelete.add(cart);
            }
        }
        repository.deleteAll(cartsToDelete);
    }

    public List<CartRespDto> convertToDtos(List<Cart> carts){
        return carts.stream()
                .map(this::convertToDto)
                .toList();
    }

    public CartRespDto convertToDto(Cart cart){
        return CartRespDto.from(cart);
    }

    @Transactional
    public void  deleteCart(CartDeleteDto dto){
        repository.deleteByCartCode(dto.getCartCode());
    }
}
