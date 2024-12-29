package project.movie.store.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import project.movie.common.handler.exception.CustomApiException;
import project.movie.store.domain.coupon.Coupon;
import project.movie.store.domain.coupon.CouponStatus;
import project.movie.store.domain.pay.Pay;
import project.movie.store.domain.pay.PayDetail;
import project.movie.store.dto.coupon.CouponRespDto;
import project.movie.store.repository.CouponRepository;


import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class CouponService {

    private final CouponRepository couponRepository;

    public void couponSave(Pay pay){

        List<PayDetail> payDetails = pay.getPayDetails();
        for (PayDetail payDetail : payDetails) {
            Coupon coupon = new Coupon();
            coupon.setCpId(Coupon.generateUUID());
            coupon.setPay(payDetail.getPay());
            coupon.setCpDate(LocalDateTime.now());
            coupon.setMemberId(pay.getMember().getMemberId());
            coupon.setCpStatus(CouponStatus.AVAILABLE);
            coupon.setItem(payDetail.getItem());
            coupon.setItemQuantity(payDetail.getCartQty());
            couponRepository.save(coupon);
        }


    }


    public List<CouponRespDto> couponList(String memberId){
        List<Coupon> coupons = couponRepository.findByMemberId(memberId);
        return convertToDtos(coupons);
    }

    public CouponRespDto getCoupon(String cpId){
        Coupon coupon = couponRepository.findByCpId(cpId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 쿠폰입니다."));
        return convertToDto(coupon);
    }

    //쿠폰 사용
    @Transactional
    public CouponRespDto useCoupon(String cpId){
        Coupon coupon = couponRepository.findByCpId(cpId)
                .orElseThrow(()->new CustomApiException("존재하지 않는 쿠폰입니다."));

        if (coupon.getCpStatus() == CouponStatus.USED){
            throw new CustomApiException("이미 사용된 쿠폰입니다.");
        }

        coupon.setCpStatus(CouponStatus.USED);

        return convertToDto(coupon);
    }


    //쿠폰 사용시 삭제
    @Transactional
    public void deleteCoupon(String cpId){
        Coupon coupon = couponRepository.findByCpId(cpId)
                .orElseThrow(() -> new CustomApiException("존재하지 않는 쿠폰입니다."));

        couponRepository.delete(coupon);
    }

    public boolean cancelBeforePayCheckCoupon(String payCode){
        List<Coupon> coupons = couponRepository.findByPay_payCode(payCode);
        for (Coupon coupon : coupons) {
            if(coupon.getCpStatus().equals(CouponStatus.USED)){
                return false;
            }
        }
        return true;
    }

    public void cancelPayAndCoupon(String payCode){
        List<Coupon>coupons = couponRepository.findByPay_payCode(payCode);
        for(Coupon coupon : coupons){
            deleteCoupon(coupon.getCpId());
        }
    }

    private CouponRespDto convertToDto(Coupon coupon){
        return CouponRespDto.from(coupon);
    }

    private List<CouponRespDto> convertToDtos (List<Coupon> coupons){
        List<CouponRespDto> dtos = new ArrayList<>();
        for(Coupon coupon: coupons){
            dtos.add(CouponRespDto.from(coupon));
        }
        return dtos;
    }

    //쿠폰 만료
    @Scheduled(cron = "0 0 0 * * ?") // 매일 자정에 실행
    public void expireCoupon(){
        LocalDateTime expirationDate = LocalDateTime.now().minusDays(120);

        List<Coupon> expiredCoupons = couponRepository.findByCpDateBeforeAndCpStatusNot(expirationDate, CouponStatus.USED);

        for (Coupon coupon : expiredCoupons) {
            coupon.setCpStatus(CouponStatus.EXPIRED);

        }
    }
}
