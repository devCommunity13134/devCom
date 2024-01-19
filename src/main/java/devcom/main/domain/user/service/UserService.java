package devcom.main.domain.user.service;


import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    public void signup(String username, String password, String email, char sex, Integer age, Integer salary, String profileImg, String skill) {
        SiteUser user = SiteUser.builder()
                .username(username)
                .password(passwordEncoder.encode(password))
                .email(email)
                .sex(sex)
                .age(age)
                .salary(salary)
                .profileImg(profileImg)
                .skill(skill)
                .build();

        this.userRepository.save(user);
    }
}


