package devcom.main.domain.message.controller;


import devcom.main.domain.message.service.MessageService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user/message")
public class MessageController {

    private final MessageService messageService;

    private final UserService userService;

    @GetMapping("/send")
    public String create(Model model, Principal principal) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        return "redirect:/user/profile";
    }


}
