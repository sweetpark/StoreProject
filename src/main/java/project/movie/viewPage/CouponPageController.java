package project.movie.viewPage;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import project.movie.store.domain.coupon.Coupon;
import project.movie.store.repository.CouponRepository;

import java.util.List;

@Controller
@RequestMapping("/page/coupons")
@RequiredArgsConstructor
public class CouponPageController {

    private final CouponRepository repository;

    @GetMapping
    public String showCouponAll(@AuthenticationPrincipal UserDetails userDetails, Model model){
        List<Coupon> coupons =  repository.findByMemberId(userDetails.getUsername());
        model.addAttribute("coupons" , coupons);
        return "store/coupons";
    }
}
