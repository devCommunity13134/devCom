package devcom.main.domain.user.controller;


import devcom.main.domain.follow.service.FollowService;
import devcom.main.domain.message.entity.ReceiveMessage;
import devcom.main.domain.message.entity.SendMessage;
import devcom.main.domain.message.service.MessageService;
import devcom.main.domain.skill.entity.Skill;
import devcom.main.domain.skill.service.SkillService;
import devcom.main.domain.user.UserCreateForm;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import devcom.main.global.email.service.EmailService;
import devcom.main.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.security.Principal;
import java.util.List;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final SkillService skillService;

    private final FollowService followService;

    private final MessageService messageService;

    private final EmailService emailService;
    private static int confirmNumber;
    private static String confirmUsername;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signupPost(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, @RequestParam(value = "profileImg") MultipartFile file) throws IOException {
        List<Skill> skillList = this.skillService.findByskillList(userCreateForm.getSkill());

        if (this.userService.checkErrors(userCreateForm, bindingResult).hasErrors()) {
            return "/user/signup";
        }
        // facade pattern : userService + skillService
        this.userService.signup(userCreateForm, skillList, file);
        SiteUser user = this.userService.findByUsername(userCreateForm.getUsername());
        this.skillService.create(userCreateForm.getSkill(), user);

        this.emailService.send(userCreateForm.getEmail(),"이메일 발송 테스트","잘 가냐?");

        //
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {

        return "user/login";
    }

    @GetMapping("/login/kakao")
    public String loginKakao() {

        return "http://localhost:8010/login/oauth2/code/kakao/";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }


    @GetMapping("/profile")
    // 나의 프로필 조회
    public String myProfile(Model model, Principal principal) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        List<SiteUser> followerUserList = this.followService.getFollowerUserList(user);
        List<SiteUser> followingUserList = this.followService.getFollowingUserList(user);
        model.addAttribute("user", user);
        model.addAttribute("followerUserList", followerUserList);
        model.addAttribute("followingUserList", followingUserList);
        return "/user/profile";
    }


    @GetMapping("/profile/{id}")
    // 다른 유저 프로필 조회
    public String userProfile(Model model, @PathVariable(value = "id") Long id) {
        SiteUser user = this.userService.findById(id);
        List<SiteUser> followerUserList = this.followService.getFollowerUserList(user);
        List<SiteUser> followingUserList = this.followService.getFollowingUserList(user);
        model.addAttribute("user", user);
        model.addAttribute("followerUserList", followerUserList);
        model.addAttribute("followingUserList", followingUserList);
        return "/user/profile";
    }

    @GetMapping("/findaccount")
    public String findAccount() {
        return "/user/find_account";
    }

    @PostMapping("/findid/email")
    public String findAccountByEmail(@RequestParam(value = "email") String emailAddress, @RequestParam(value = "usernameEmail") String username) {
        confirmUsername = username;
        return "/user/confirm_form";
    }

    @PostMapping("/findid/confirm")
    public String confirm(Model model, @RequestParam(value = "confirmNum") int confirmNum) {
        if (confirmNum != confirmNumber) {
            return "/findid/confirm";
        }
        SiteUser user = this.userService.findByUsername(confirmUsername);
        model.addAttribute("user", user);
        return "redirect:/user/confirm_form";
    }

    @GetMapping("/follow/{id}")
    public String followUser(Model model, Principal principal, @PathVariable(value = "id") Long id) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        this.followService.addFollower(user, id);
        this.followService.addFollowing(user, id);
        return String.format("redirect:/user/profile/%d", id);
    }

    @GetMapping("/message")
    public String messageList(Model model, Principal principal) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        List<SendMessage> sendMessageList = user.getSendMessageList();
        List<ReceiveMessage> receiveMessageList = user.getReceiveMessageList();
        model.addAttribute("sendMessageList",sendMessageList);
        model.addAttribute("receiveMessageList",receiveMessageList);
        return "/user/message_list";
    }

}
