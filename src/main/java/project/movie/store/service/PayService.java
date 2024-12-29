package project.movie.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.common.web.response.ResponseDto;
import project.movie.member.domain.Member;
import project.movie.member.service.MemberService;

import project.movie.store.domain.PG.IamPortClient;
import project.movie.store.domain.cart.Cart;
import project.movie.store.domain.item.Item;
import project.movie.store.domain.pay.Pay;
import project.movie.store.domain.pay.PayDetail;
import project.movie.store.domain.pay.PayStatus;
import project.movie.store.dto.PG.IamportResponseDto;
import project.movie.store.dto.PG.PaymentCompleteDto;
import project.movie.store.dto.PG.PaymentRequestDto;
import project.movie.store.dto.cart.CartPurchaseDto;
import project.movie.store.dto.cart.PurchaseByOneDto;
import project.movie.store.dto.pay.PayRespDto;
import project.movie.store.repository.PayDetailRepository;
import project.movie.store.repository.PayRepository;

import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class PayService {


    private final PayRepository payRepository;
    private final PayDetailRepository payDetailRepository;
    private final MemberService memberService;
    private final CouponService couponService;
    private final IamPortClient iamPortClient;
    private final ItemService itemService;
    private final CartService cartService;


    @Transactional
    public PaymentRequestDto payCreate(List<CartPurchaseDto> cartPurchaseDtos, String memberId){
        Member findMember = memberService.getByMemberId(memberId);
        int totalPrice = 0;

        Pay pay = new Pay();
        pay.generatePayCode();
        //카드 고정
        pay.setPayType("card");
        pay.setMember(findMember);
        pay.setPayDate(LocalDateTime.now());
        pay.setPayStatus(PayStatus.CART_CREATE);
        pay.setPayPrice(0);
        payRepository.save(pay);

        for( CartPurchaseDto cartPurchaseDto : cartPurchaseDtos){
            Cart findCart =  cartService.findByCartCode(cartPurchaseDto.getCartCode());
            PayDetail payDetail = new PayDetail();
            payDetail.setPay(pay);
            payDetail.setItem(findCart.getItem());
            payDetail.setCartQty(findCart.getCartQty());
            payDetailRepository.save(payDetail);
            totalPrice += ((findCart.getItem().getPrice() - findCart.getItem().getSalePrice()) * findCart.getCartQty());
        }

        pay.setPayPrice(totalPrice);
        payRepository.save(pay);

        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setPayCode(pay.getPayCode());
        paymentRequestDto.setTotalPrice(pay.getPayPrice());
        paymentRequestDto.setStatus(PayStatus.CART_CREATE);
        paymentRequestDto.setMemberUsername(pay.getMember().getUsername());
        paymentRequestDto.setImpUid(iamPortClient.getImpPortUid());

        return paymentRequestDto;
    }

    @Transactional
    public PaymentRequestDto payCreateByOne(PurchaseByOneDto purchaseByOneDto, String memberId){
        Member findMember = memberService.getByMemberId(memberId);
        int totalPrice = 0;

        Pay pay = new Pay();
        pay.generatePayCode();
        pay.setMember(findMember);
        pay.setPayType("card");
        pay.setPayDate(LocalDateTime.now());
        pay.setPayStatus(PayStatus.DIRECT_CREATE);
        pay.setPayPrice(0);
        payRepository.save(pay);

        Item findItem =  itemService.itemFindByItemCode(purchaseByOneDto.getItemCode());
        PayDetail payDetail = new PayDetail();
        payDetail.setPay(pay);
        payDetail.setItem(findItem);
        payDetail.setCartQty(purchaseByOneDto.getItemQty());
        payDetailRepository.save(payDetail);
        totalPrice += ((findItem.getPrice() - findItem.getSalePrice()) * purchaseByOneDto.getItemQty());


        pay.setPayPrice(totalPrice);
        payRepository.save(pay);

        PaymentRequestDto paymentRequestDto = new PaymentRequestDto();
        paymentRequestDto.setPayCode(pay.getPayCode());
        paymentRequestDto.setTotalPrice(pay.getPayPrice());
        paymentRequestDto.setStatus(PayStatus.DIRECT_CREATE);
        paymentRequestDto.setMemberUsername(pay.getMember().getUsername());
        paymentRequestDto.setImpUid(iamPortClient.getImpPortUid());

        return paymentRequestDto;
    }


    @Transactional
    public IamportResponseDto verifyResponse(PaymentCompleteDto dto){

        try{
            IamportResponseDto response = iamPortClient.paymentByImpUid(dto.getImpUid());

            if (response == null || response.getResponse() == null) {
                throw new CustomApiException("결제 정보가 유효하지 않습니다.");
            }

            // Print status for debugging
            System.out.println(response.getResponse().getStatus());



            if("paid".equals(response.getResponse().getStatus())){

                Pay findPay = getFindByPayCode(response.getResponse().getMerchant_uid());


                if (findPay == null) {
                    throw new CustomApiException("결제 정보를 찾을 수 없습니다.");
                }

                // Check for price mismatch
                if (findPay.getPayPrice() != null && !findPay.getPayPrice().equals(response.getResponse().getAmount())) {
                    cancelPGResponse(dto.getImpUid(),  "결제 금액 불일치로 인한 취소");
                    throw new CustomApiException("결제 금액 오류");
                }

                // Process cart if status is "CART_CREATE"
                if (findPay.getPayStatus().equals(PayStatus.CART_CREATE)) {
                    cartService.paidCart(findPay);
                }

                // Update payment status
                findPay.setPayStatus(PayStatus.PAID);
                findPay.setImpCode(dto.getImpUid());

                payRepository.save(findPay);

                // Save coupon
                couponService.couponSave(findPay);

                return response;
            }else{
                throw new CustomApiException("결제 오류");
            }
        }catch (Exception e){
            throw new CustomApiException("결제 중 오류로 인한 결제 취소");
        }


    }


    private void cancelPGResponse(String impUid, String reason){
        try{
            IamportResponseDto response = iamPortClient.cancelPaymentByImpUid(impUid, reason);

            if(!"cancelled".equals(response.getResponse().getStatus())){
                throw new CustomApiException("결제 취소 실패 : PG서버 응답 오류");
            }
        }catch (Exception e){
            throw new CustomApiException("결제 취소중 오류" + e.getMessage());
        }
    }

    @Transactional
    public List<Pay> getPayInfoAllByMember(String memberId){
        List<Pay> payList = payRepository.findByMember_memberId(memberId);
        // 예외를 던지는 경우
//        if (payList.isEmpty()) {
//            throw new CustomApiException("결제 내역이 존재하지 않습니다.");
//        }
        return payList;
    }


    //결제정보 조회
    public PayRespDto  getPayInfo(String payCode){

        Pay pay = payRepository.findByPayCode(payCode)
                .orElseThrow(() -> new CustomApiException("결제코드가 존재하지 않습니다")
                );

        return PayRespDto.from(pay);
    }



    public List<PayRespDto> convertToDtos(List<Pay> pays){
        List<PayRespDto> payRespDtos = new ArrayList<>();
        for(Pay pay : pays){
            payRespDtos.add(PayRespDto.from(pay));
        }
        return payRespDtos;
    }




    @Transactional
    //결제정보 업데이트 ( 결제 취소 )
    public ResponseDto<?> cancelPayment(String payCode){
        Optional<Pay> payOptional = payRepository.findById(payCode);
        if(payOptional.isPresent()){
            Pay pay = payOptional.get();
            if(!payDateOlderOneWeek(pay.getPayDate())){
                cancelPGResponse(pay.getImpCode(), "결제 취소 요청");

                couponService.cancelPayAndCoupon(pay.getPayCode());
                pay.setPayStatus(PayStatus.CANCEL);
                payRepository.save(pay);
                return new ResponseDto<>(1,"취소 성공", null);
            }
            PayRespDto dto = PayRespDto.from(pay);
            return new ResponseDto<>(1, "결제 후 일주일 지난 결제 취소 불가", dto);
        }else{
            throw new CustomApiException("결제 정보를 찾을 수 없습니다");
        }
    }

    //결제정보 삭제
    public void deletePayment(String payCode){
        if (payRepository.existsById(payCode)){
            payRepository.deleteById(payCode);
        }else{
            throw new CustomApiException("결제 정보를 찾을 수 없습니다");
        }
    }


    public Pay getFindByPayCode(String payCode){
        return payRepository.findById(payCode)
                .orElseThrow(() -> new CustomApiException("결제 정보가 존재하지 않습니다."));
    }

    private boolean payDateOlderOneWeek(LocalDateTime payDate){
        LocalDateTime now = LocalDateTime.now();
        long daysBetween = ChronoUnit.DAYS.between(payDate, now);
        return daysBetween > 7;
    }
}
