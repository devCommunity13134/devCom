package devcom.main.domain.user.controller;


import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;
}
