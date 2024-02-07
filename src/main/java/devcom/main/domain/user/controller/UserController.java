package devcom.main.domain.user.controller;


import devcom.main.domain.follow.service.FollowService;
import devcom.main.domain.message.entity.ReceiveMessage;
import devcom.main.domain.message.entity.SendMessage;
import devcom.main.domain.message.service.MessageService;
import devcom.main.domain.skill.entity.Skill;
import devcom.main.domain.skill.service.SkillService;
import devcom.main.domain.user.ConfirmNumberForm;
import devcom.main.domain.user.EmailConfirmForm;
import devcom.main.domain.user.UserCreateForm;
import devcom.main.domain.user.UserModifyForm;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import devcom.main.global.email.service.EmailService;
import devcom.main.global.rq.Rq;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
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

    private static String confirmName;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signupPost(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, @RequestParam(value = "profileImg") MultipartFile file) throws IOException {
        List<Skill> skillList = null;
        if(userCreateForm.getSkill()!=null) {
            skillList = this.skillService.findByskillList(userCreateForm.getSkill());
        }
        if (this.userService.checkErrors(userCreateForm, bindingResult).hasErrors()) {
            return "/user/signup";
        }
        try {
            // facade pattern : userService + skillService
            this.userService.signup(userCreateForm, skillList, file);
            SiteUser user = this.userService.findByUsername(userCreateForm.getUsername());
            this.skillService.create(userCreateForm.getSkill(), user);
            this.emailService.send(userCreateForm.getEmail(),"[devCom]회원가입을 환영합니다!","[devCom] 서비스에 가입해주셔서 감사합니다.");
            //
        } catch(DataIntegrityViolationException e) {
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "/user/signup";
        }catch(Exception e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "/user/signup";
        }
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
    public String userProfile(Model model, @PathVariable(value = "id") Long id, Principal principal) {
        if (principal == null) {
            return "redirect:/user/login";
        }
        SiteUser user = this.userService.findById(id);
        SiteUser loginedUser = this.userService.findByUsername(principal.getName());
        List<SiteUser> followerUserList = this.followService.getFollowerUserList(user);
        List<SiteUser> followingUserList = this.followService.getFollowingUserList(user);
        String isFollow = this.userService.isFollow(loginedUser, id);
        model.addAttribute("isFollow", isFollow);
        model.addAttribute("user", user);
        model.addAttribute("followerUserList", followerUserList);
        model.addAttribute("followingUserList", followingUserList);
        return "/user/profile";
    }

    @GetMapping("/modify")
    public String modifyProfile(UserModifyForm userModifyForm, Model model, Principal principal) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        model.addAttribute("user", user);
        return "/user/modify_profile";
    }

    @PostMapping("/modify/{id}")
    public String modifyProfileAccept(Model model, @Valid UserModifyForm userModifyForm, BindingResult bindingResult,
                                      @PathVariable(value = "id") Long id,
                                      @RequestParam(value = "profileImg") MultipartFile file) throws IOException {
        SiteUser modifyUser = this.userService.findById(id);
        model.addAttribute("user", modifyUser);
        if (this.userService.checkModifyErrors(userModifyForm, bindingResult).hasErrors()) {
            return "/user/modify_profile";
        }
        List<Skill> skillList = this.skillService.findByskillList(userModifyForm.getSkill());
        try {
            // facade pattern : userService + skillService
            this.userService.modify(userModifyForm, modifyUser, skillList, file);
            SiteUser user = this.userService.findByUsername(modifyUser.getUsername());
            this.skillService.modify(userModifyForm.getSkill(), user);
            //
        } catch(DataIntegrityViolationException e) {
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "/user/modify_profile";
        }catch(Exception e) {
            bindingResult.reject("signupFailed", e.getMessage());
            return "/user/modify_profile";
        }
        return this.logout();
    }

    @GetMapping("/findaccount")
    public String findAccount(EmailConfirmForm emailConfirmForm) {
        return "/user/find_account";
    }

    @PostMapping("/findid")
    public String findAccountByEmail(ConfirmNumberForm confirmNumberForm, @Valid EmailConfirmForm emailConfirmForm, BindingResult bindingResult, @RequestParam(value = "usernameEmail") String username) {
        confirmName = username;
        SiteUser user = this.userService.findByNickname(username);
        if (this.userService.checkEmailAndUser(emailConfirmForm, bindingResult, user.getEmail()).hasErrors()) {
            return "/user/find_account";
        }
        confirmNumber = this.emailService.sendConfirm(emailConfirmForm.getEmail());
        return "/user/confirm_form";
    }

    @PostMapping("/findid/confirm")
    public String confirmId(Model model, @Valid ConfirmNumberForm confirmNumberForm, BindingResult bindingResult) {
        if (this.userService.checkErrors(confirmNumberForm, bindingResult, confirmNumber).hasErrors()) {
            return "/user/confirm_form";
        }

        SiteUser user = this.userService.findByNickname(confirmName);
        model.addAttribute("user", user);

        return "/user/confirm_form";
    }

    @PostMapping("/findpw")
    public String findPasswordByEmail(ConfirmNumberForm confirmNumberForm, @Valid EmailConfirmForm emailConfirmForm, BindingResult bindingResult, @RequestParam(value = "userid") String userId) {
        confirmName = userId;
        SiteUser user = this.userService.findByUsername(userId);
        if (this.userService.checkEmailAndUser(emailConfirmForm, bindingResult, user.getEmail()).hasErrors()) {
            return "/user/find_account";
        }
        confirmNumber = this.emailService.sendConfirm(emailConfirmForm.getEmail());
        return "/user/confirm_form_pw";
    }

    @PostMapping("/findpw/confirm")
    public String confirmPw(Model model, @Valid ConfirmNumberForm confirmNumberForm, BindingResult bindingResult) {
        if (this.userService.checkErrors(confirmNumberForm, bindingResult, confirmNumber).hasErrors()) {
            return "/user/confirm_form_pw";
        }

        SiteUser user = this.userService.findByUsername(confirmName);
        String pw = this.userService.modifyPw(user);

        model.addAttribute("pw", pw);

        return "/user/confirm_form_pw";
    }

    @GetMapping("/follow/{id}")
    public String followUser(Model model, Principal principal, @PathVariable(value = "id") Long id) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        this.followService.addFollower(user, id);
        this.followService.addFollowing(user, id);
        return String.format("redirect:/user/profile/%d", id);
    }

    @GetMapping("/unfollow/{id}")
    public String unfollowUser(Model model, Principal principal, @PathVariable(value = "id") Long id) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        // 쟤의 팔로워, 나의 팔로잉을 삭제해야한다
        this.followService.removeFollower(this.userService.findById(id), user.getId());
        this.followService.removeFollowing(user, id);
        return String.format("redirect:/user/profile/%d", id);
    }

    @GetMapping("/message")
    // 받은 쪽지 화면(default)
    public String messageList(Model model, Principal principal, @RequestParam(value = "sendpage", defaultValue = "0") int sendpage, @RequestParam(value = "receivepage", defaultValue = "0") int receivepage) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        Page<ReceiveMessage> receiveMessageList = this.messageService.getReceiveMessageList(receivepage,user);
        model.addAttribute("receiveMessageList", receiveMessageList);
        return "/user/message_list";
    }

    @GetMapping("/message/send")
    // 보낸 쪽지 화면
    public String sendMessageList(Model model, Principal principal, @RequestParam(value = "page", defaultValue = "0") int page) {
        SiteUser user = this.userService.findByUsername(principal.getName());
        Page<SendMessage> sendMessageList = this.messageService.getSendMessageList(page, user);
        model.addAttribute("sendMessageList", sendMessageList);
        return "/user/send_message_list";
    }

}
