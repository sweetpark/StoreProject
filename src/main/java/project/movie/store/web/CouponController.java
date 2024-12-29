package project.movie.store.web;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import project.movie.common.web.response.ResponseDto;
import project.movie.store.domain.coupon.Coupon;
import project.movie.store.dto.coupon.CouponRespDto;
import project.movie.store.service.CouponService;

import java.util.List;

@RequiredArgsConstructor
@RestController
@Slf4j
@RequestMapping("/api/coupon")
public class CouponController {

    private final CouponService couponService;

    @GetMapping
    public ResponseEntity<?> couponList(@AuthenticationPrincipal UserDetails userDetails){
        List<CouponRespDto> coupons = couponService.couponList(userDetails.getUsername());
        return new ResponseEntity<>(new ResponseDto<>(1, "쿠폰 조회 성공", coupons), HttpStatus.OK);
    }

    @GetMapping("/{couponId}")
    public ResponseEntity<?> getCoupon(@PathVariable String couponId){
        CouponRespDto coupon = couponService.getCoupon(couponId);
        return new ResponseEntity<>(new ResponseDto<>(1, "쿠폰 개별 조회 성공", coupon), HttpStatus.OK);
    }


    //쿠폰 사용
    @PutMapping("/{couponId}/use")
    public ResponseEntity<?> useCoupon(@PathVariable String couponId){
        System.out.println(couponId);
        CouponRespDto couponRespDto = couponService.useCoupon(couponId);
//        return new ResponseEntity<>(new ResponseDto<>(1, "쿠폰 사용 완료", couponRespDto), HttpStatus.OK);
        return new ResponseEntity<>(new ResponseDto<>(1, "쿠폰 사용 완료", null), HttpStatus.OK);
    }

    //쿠폰삭제
    @DeleteMapping("/{couponId}")
    public ResponseEntity<?> deleteCoupon(@PathVariable String couponId){
        couponService.deleteCoupon(couponId);
        return new ResponseEntity<>(new ResponseDto<>(1, "쿠론 삭제 완료", null), HttpStatus.OK);
    }

}
