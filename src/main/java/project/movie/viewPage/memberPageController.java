package project.movie.viewPage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/page")
public class memberPageController {

    @GetMapping("join")
    public String getJoin(){
        return "member/login/memberJoin";
    }
}
