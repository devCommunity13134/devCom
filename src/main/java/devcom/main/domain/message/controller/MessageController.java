package devcom.main.domain.message.controller;


import devcom.main.domain.message.service.MessageService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;

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

    // 보낸 쪽지 삭제
    @PostMapping("/remove/send")
    public String removeSendMessage(@RequestParam(value = "id") List<String> messageIdList) {
        this.messageService.removeSendMessage(messageIdList);
        return "redirect:/user/message";
    }


    // 받은 쪽지 삭제
    @PostMapping("/remove/receive")
    public String removeReceiveMessage(@RequestParam(value = "id") List<String> messageIdList) {
        this.messageService.removeReceiveMessage(messageIdList);
        return "redirect:/user/message";
    }




}
