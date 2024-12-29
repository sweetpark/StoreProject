package project.movie.store.web;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import project.movie.common.web.response.ResponseDto;
import project.movie.store.domain.pay.Pay;
import project.movie.store.dto.PG.IamportResponseDto;
import project.movie.store.dto.PG.PaymentCompleteDto;
import project.movie.store.dto.PG.PaymentRequestDto;
import project.movie.store.dto.cart.CartPurchaseDto;
import project.movie.store.dto.cart.CartRespDto;
import project.movie.store.dto.cart.PurchaseByOneDto;
import project.movie.store.dto.pay.PayRespDto;
import project.movie.store.service.CouponService;
import project.movie.store.service.PayService;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/pay")
public class PayController {

    private final PayService payService;
    private final CouponService couponService;

    //결제 구매하기 전 결제 구매 생성 (장바구니 구매)
    @PostMapping("/cart/purchase/create")
    public ResponseEntity<?> purchaseByCart(@RequestBody List<CartPurchaseDto> cartPurchaseDtos, @AuthenticationPrincipal UserDetails userDetails){
        PaymentRequestDto paymentRequestDto = payService.payCreate(cartPurchaseDtos, userDetails.getUsername());
        return new ResponseEntity<>(new ResponseDto<>(1, "장바구니 주문번호 생성 완료", paymentRequestDto), HttpStatus.OK);
    }

    //결제 구매하기 전 결제 구매 생성 (바로 구매)
    @PostMapping("/direct/purchase/create")
    public ResponseEntity<?> purchaseByOne(@RequestBody PurchaseByOneDto purchaseByOneDto, @AuthenticationPrincipal UserDetails userDetails){
        PaymentRequestDto paymentRequestDto = payService.payCreateByOne(purchaseByOneDto, userDetails.getUsername());
        return new ResponseEntity<>(new ResponseDto<>(1, "바로구매 주문번호 생성 완료", paymentRequestDto), HttpStatus.OK);
    }


    //결제 완료 확인
    @PostMapping("/payment/complete")
    public ResponseEntity<?> payCheck(@RequestBody PaymentCompleteDto dto){
        IamportResponseDto response =  payService.verifyResponse(dto);
        return new ResponseEntity<>(new ResponseDto<>(1, "결제 성공", response), HttpStatus.OK);
    }


    @GetMapping("/{payCode}")
    public ResponseEntity<?> paidPage(@PathVariable String orderId ){
        PayRespDto payRespDto = payService.getPayInfo(orderId);
        return new ResponseEntity<>(new ResponseDto<>(1,"결제 완료 페이지 조회 성공", payRespDto), HttpStatus.OK);
    }

    @GetMapping
    public ResponseEntity<?> getPayInfoAllByMember(@AuthenticationPrincipal UserDetails userDetails){
        List<Pay> pays = payService.getPayInfoAllByMember(userDetails.getUsername());
        List<PayRespDto> payRespDtos = payService.convertToDtos(pays);
        return new ResponseEntity<>(new ResponseDto<>(1,"결제 정보 리스트 조회 성공", payRespDtos),HttpStatus.OK);
    }

    @PutMapping("/cancel/{payCode}")
    public ResponseEntity<?> cancelPayment(@PathVariable("payCode") String payCode, @AuthenticationPrincipal UserDetails userDetails){
        List<Pay> pays = payService.getPayInfoAllByMember(userDetails.getUsername());

        for (Pay pay : pays) {
            if (Objects.equals(pay.getPayCode(), payCode) && couponService.cancelBeforePayCheckCoupon(pay.getPayCode())){
                return new ResponseEntity<>(payService.cancelPayment(payCode),HttpStatus.OK);
            }
        }

        return new ResponseEntity<>(new ResponseDto<>(-1, "결제코드가 존재하지 않거나, 이미 사용한 쿠폰이 존재합니다.", null), HttpStatus.OK);
    }



}
