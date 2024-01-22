package devcom.main.domain.user.service;


import devcom.main.domain.skill.entity.Skill;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final PasswordEncoder passwordEncoder;
    public void signup(String username, String nickname, String password, String email, char sex, Integer age, Integer salary, String profileImg, Integer phoneNumber) {
        SiteUser user = SiteUser.builder()
                .username(username)
                .nickname(nickname)
                .password(passwordEncoder.encode(password))
                .phoneNumber(phoneNumber)
                .email(email)
                .sex(sex)
                .age(age)
                .salary(salary)
                .profileImg(profileImg)
                .build();

        this.userRepository.save(user);
    }

    public SiteUser findByusername(String username) {
        Optional<SiteUser> user = this.userRepository.findByusername(username);
        if(user.isEmpty()) {
            throw new RuntimeException("존재하지 않은 사용자입니다.");
        }
        return user.get();
    }

    public SiteUser findById(Long id) {
        Optional<SiteUser> user = this.userRepository.findById(id);
        if(user.isEmpty()) {
            throw new RuntimeException("존재하지 않은 사용자입니다.");
        }
        return user.get();
    }
}


