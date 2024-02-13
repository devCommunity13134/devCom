package devcom.main.domain.message.controller;


import devcom.main.domain.message.RemoveMessageForm;
import devcom.main.domain.message.entity.ReceiveMessage;
import devcom.main.domain.message.entity.SendMessage;
import devcom.main.domain.message.service.MessageService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.Banner;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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

    @PostMapping("/send/reply/{id}")
    public String sendReplyMessage(Model model, Principal principal, @PathVariable(value = "id") Long id, @RequestParam(value = "message-text") String content) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        this.messageService.addSendMessage(user, id, content);
        this.messageService.addReceiveMessage(this.userService.findById(id), user.getId(), content);
        model.addAttribute("user",user);
        return "redirect:/user/message";
    }

    // 보낸 쪽지 삭제
    @PostMapping("/remove/send")
    public String removeSendMessage(@Valid RemoveMessageForm removeMessageForm, BindingResult bindingResult, Model model, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page) {
        if (removeMessageForm.getId() == null) {
            bindingResult.reject("idIsEmpty","삭제할 쪽지를 선택해주세요.");
            SiteUser user = this.userService.findByUsername(principal.getName());
            Page<SendMessage> sendMessageList = this.messageService.getSendMessageList(page, user);
            model.addAttribute("sendMessageList", sendMessageList);
            return "/user/send_message_list";
        }
        this.messageService.removeSendMessage(removeMessageForm.getId());
        return "redirect:/user/message/send";
    }

    // 받은 쪽지 삭제
    @PostMapping("/remove/receive")
    public String removeReceiveMessage(@Valid RemoveMessageForm removeMessageForm, BindingResult bindingResult, Model model, Principal principal, @RequestParam(value = "sendpage", defaultValue = "0") int sendpage, @RequestParam(value = "receivepage", defaultValue = "0") int receivepage) {
        if (removeMessageForm.getId() == null) {
            bindingResult.reject("idIsEmpty","삭제할 쪽지를 선택해주세요.");
            SiteUser user = this.userService.findByUsername(principal.getName());
            Page<ReceiveMessage> receiveMessageList = this.messageService.getReceiveMessageList(receivepage,user);
            model.addAttribute("receiveMessageList", receiveMessageList);
            return "/user/message_list";
        }
        this.messageService.removeReceiveMessage(removeMessageForm.getId());
        return "redirect:/user/message";
    }

    @GetMapping("/receive/detail/{id}")
    public String receiveMessageDetail(Model model, @PathVariable(value = "id") Long id) {
        ReceiveMessage rm = this.messageService.findRmById(String.valueOf(id));
        this.messageService.receiveMessageChecked(rm);
        model.addAttribute("Message",rm);
        return "/user/receive_message_detail";
    }

    @GetMapping("/send/detail/{id}")
    public String sendMessageDetail(Model model, @PathVariable(value = "id") Long id) {
        SendMessage sm = this.messageService.findSmById(String.valueOf(id));
        model.addAttribute("Message",sm);
        return "/user/send_message_detail";
    }




}
