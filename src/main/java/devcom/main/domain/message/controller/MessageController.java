package devcom.main.domain.message.controller;


import devcom.main.domain.message.service.MessageService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/message")
public class MessageController {

    private final MessageService messageService;

    private final UserService userService;

    @PostMapping("/send/{id}")
    public String sendMessage(Model model, Principal principal, @PathVariable(value = "id") Long id, @RequestParam(value = "message-text") String content) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        this.messageService.addSendMessage(user, id, content);
        this.messageService.addReceiveMessage(this.userService.findById(id), user.getId(), content);
        model.addAttribute("user",user);
        return String.format("redirect:/user/profile/%d",id);
    }


}
