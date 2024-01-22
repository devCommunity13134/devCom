package devcom.main.domain.user.controller;


import devcom.main.domain.skill.entity.Skill;
import devcom.main.domain.skill.service.SkillService;
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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.security.Principal;
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
                    ,userCreateForm.getProfileImg(),userCreateForm.getPhoneNumber());
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

    @GetMapping("/findaccount")
    public String findAccount() {
        return "/user/find_account";
    }

    private Map<String, String> skillList() {
        Map<String, String> map = new LinkedHashMap<>();
        map.put("HTML", "HTML");
        map.put("CSS", "CSS");
        map.put("JS", "JS");
        map.put("C", "C");
        map.put("C++", "C++");
        map.put("Java", "Java");
        map.put("Python", "Python");
        map.put("SQL", "SQL");
        return map;
    }


}
