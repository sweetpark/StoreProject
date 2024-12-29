package project.movie.store.web;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class PgTestController {

    @GetMapping("/test")
    public String test(){
        return "store/PgConnect";
    }

}
