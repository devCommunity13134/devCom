package devcom.main.domain.home;


import devcom.main.domain.article.service.ArticleService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
public class HomeController {

    private final UserService userService;
    private final ArticleService articleService;

    @GetMapping("/")
    public String root(Model model, Principal principal) {
        if(principal != null) {
            SiteUser user = this.userService.findByUsername(principal.getName());
            model.addAttribute("user",user);
        }

        return "home/index";
    }
}
