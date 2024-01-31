package devcom.main.domain.user.service;

import devcom.main.domain.follow.entity.Following;
import devcom.main.domain.follow.repository.FollowingRepository;
import devcom.main.domain.skill.entity.Skill;
import devcom.main.domain.user.UserCreateForm;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;
    private final FollowingRepository followingRepository;

    private final PasswordEncoder passwordEncoder;
    public void signup(UserCreateForm userCreateForm, List<Skill> skillList, MultipartFile file) throws IOException {
        file.transferTo(new File("C:\\Work\\devCom\\src\\main\\resources\\static\\images\\"+file.getOriginalFilename()));
        String profileImg = "/images/"+file.getOriginalFilename();
        SiteUser user = SiteUser.builder()
                .username(userCreateForm.getUsername())
                .nickname(userCreateForm.getNickname())
                .password(passwordEncoder.encode(userCreateForm.getPassword2()))
                .phoneNumber(userCreateForm.getPhoneNumber())
                .email(userCreateForm.getEmail())
                .sex(userCreateForm.getSex())
                .age(userCreateForm.getAge())
                .salary(userCreateForm.getSalary())
                .profileImg(profileImg)
                .skillList(skillList)
                .build();
        this.userRepository.save(user);
    }

    public void kakaoSignup(String username, String nickname) {
        SiteUser user = SiteUser.builder()
                .username(username)
                .nickname(nickname)
                .password("1234")
                .phoneNumber("01012341234")
                .email("kakao@test.com")
                .sex('남')
                .age(99)
                .salary(9999)
                .profileImg("https://www.designdb.com/usr/upload/editor/email/202304132212562023ad50-d888-46ee-8247-12fe63822d4f.png")
                .skillList(null)
                .build();

        this.userRepository.save(user);
    }

    public Optional<SiteUser> findByNickname(String nickname) {
        Optional<SiteUser> user = this.userRepository.findByNickname(nickname);
        return user;
    }

    public SiteUser findByUsername(String username) {
        Optional<SiteUser> user = this.userRepository.findByUsername(username);
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

    public BindingResult checkErrors(UserCreateForm userCreateForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return bindingResult;
        }
        if(!userCreateForm.getPassword1().equals(userCreateForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return bindingResult;
        }
        return bindingResult;
    }


    @Transactional
    public SiteUser whenSocialLogin(String providerTypeCode, String username, String nickname) {

        // 소셜 로그인를 통한 가입시 비번은 없다.
        this.kakaoSignup(username, nickname);

        return findByUsername(username);
        // 최초 로그인 시 딱 한번 실행
    }
}


