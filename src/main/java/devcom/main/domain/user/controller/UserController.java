package devcom.main.domain.user.controller;


import devcom.main.domain.user.UserCreateForm;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/signup")
    public String signup(UserCreateForm userCreateForm) {
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signupPost(@Valid UserCreateForm userCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return "user/signup";
        }
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return "user/signup";
        }
        try {
            this.userService.signup(userCreateForm.getUsername(), userCreateForm.getNickname(),userCreateForm.getPassword2()
                    ,userCreateForm.getEmail(),userCreateForm.getSex(),userCreateForm.getAge(),userCreateForm.getSalary()
                    ,userCreateForm.getProfileImg(),userCreateForm.getSkill());
        } catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "user/signup";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "user/signup";
        }
        return "redirect:/";
    }

    @GetMapping("/login")
    public String login() {
        return "/user/login";
    }

    @GetMapping("/logout")
    public String logout() {
        return "redirect:/";
    }

    @GetMapping("/profile")
    public String profile(Model model, Principal principal) {
        SiteUser user = this.userService.findByusername(principal.getName());
        model.addAttribute("user",user);
        return "/user/profile";
    }

}
