package devcom.main.domain.user.controller;


import devcom.main.domain.user.UserCreateForm;
import devcom.main.domain.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

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
            bindingResult.reject("passwordIncorrect","2개의 비밀번호가 일치하지 않습니다.");
        }
        this.userService.signup(userCreateForm.getUsername(),userCreateForm.getPassword2()
        ,userCreateForm.getEmail(),userCreateForm.getSex(),userCreateForm.getAge(),userCreateForm.getSalary()
        ,userCreateForm.getProfileImg(),userCreateForm.getSkill());
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
}
