package devcom.main.domain.user.controller;


import devcom.main.domain.skill.entity.Skill;
import devcom.main.domain.skill.service.SkillService;
import devcom.main.domain.user.UserCreateForm;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.security.Principal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Controller
@RequiredArgsConstructor
@RequestMapping("/user")
public class UserController {

    private final UserService userService;

    private final SkillService skillService;

    @GetMapping("/signup")
    public String signup(Model model, UserCreateForm userCreateForm) {
        List<Skill> skillList = this.skillService.findAll();
        model.addAttribute("skillList",skillList);
        return "user/signup";
    }

    @PostMapping("/signup")
    public String signupPost(@Valid UserCreateForm userCreateForm, BindingResult bindingResult, @RequestParam(value = "skill") List<String> skills) {
        List<Skill> skillList = this.skillService.findByskillList(skills);
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
                    ,userCreateForm.getProfileImg(),userCreateForm.getPhoneNumber(),skillList);
        } catch(DataIntegrityViolationException e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", "이미 등록된 사용자입니다.");
            return "user/signup";
        }catch(Exception e) {
            e.printStackTrace();
            bindingResult.reject("signupFailed", e.getMessage());
            return "user/signup";
        }
        SiteUser user = this.userService.findByUsername(userCreateForm.getUsername());
        this.skillService.create(skills,user);
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
        SiteUser user = this.userService.findByUsername(principal.getName());
        model.addAttribute("user",user);
        return "/user/profile";
    }

    @GetMapping("/findaccount")
    public String findAccount() {
        return "/user/find_account";
    }

}
