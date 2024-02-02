package devcom.main.domain.user.service;


import devcom.main.domain.follow.repository.FollowingRepository;
import devcom.main.domain.message.entity.SendMessage;
import devcom.main.domain.message.repository.SendMessageRepository;
import devcom.main.domain.skill.entity.Skill;
import devcom.main.domain.user.ConfirmNumberForm;
import devcom.main.domain.user.EmailConfirmForm;
import devcom.main.domain.user.UserCreateForm;
import devcom.main.domain.user.UserModifyForm;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.BindingResult;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    private final FollowingRepository followingRepository;

    private final SendMessageRepository sendMessageRepository;

    private final PasswordEncoder passwordEncoder;

    @Value("${custom.fileDirPath}")
    private String fileDirPath;


    public void signup(UserCreateForm userCreateForm, List<Skill> skillList, MultipartFile file) throws IOException {
        // 프로젝트 내부 저장
        String localProfileImg = UUID.randomUUID().toString() + file.getOriginalFilename();
        file.transferTo(new File("C:\\Work\\devCom\\src\\main\\resources\\static\\images\\" + localProfileImg));
        String localProfileImgPath = "/images/" + localProfileImg;
        // 외부 저장
        // String outOflocalProfileImg = fileDirPath + UUID.randomUUID().toString() + file.getOriginalFilename();
        // file.transferTo(new File(outOflocalProfileImg));
        SiteUser user = SiteUser.builder()
                .username(userCreateForm.getUsername())
                .nickname(userCreateForm.getNickname())
                .password(passwordEncoder.encode(userCreateForm.getPassword2()))
                .phoneNumber(userCreateForm.getPhoneNumber())
                .email(userCreateForm.getEmail())
                .sex(userCreateForm.getSex())
                .age(userCreateForm.getAge())
                .salary(userCreateForm.getSalary())
                .profileImg(localProfileImgPath)
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

    public void modify(UserModifyForm userModifyForm, SiteUser modifyUser, List<Skill> skillList, MultipartFile file) throws IOException {
        // 프로젝트 내부 저장
        String localProfileImg = UUID.randomUUID().toString() + file.getOriginalFilename();
        file.transferTo(new File("C:\\Work\\devCom\\src\\main\\resources\\static\\images\\" + localProfileImg));
        String localProfileImgPath = "/images/" + localProfileImg;
        // 외부 저장
        // String outOflocalProfileImg = fileDirPath + UUID.randomUUID().toString() + file.getOriginalFilename();
        // file.transferTo(new File(outOflocalProfileImg));
        SiteUser user = modifyUser.toBuilder()
                .nickname(userModifyForm.getNickname())
                .password(passwordEncoder.encode(userModifyForm.getPassword2()))
                .phoneNumber(userModifyForm.getPhoneNumber())
                .email(userModifyForm.getEmail())
                .salary(userModifyForm.getSalary())
                .profileImg(localProfileImgPath)
                .skillList(skillList)
                .build();

        this.userRepository.save(user);
    }

    public SiteUser findByUsername(String username) {
        Optional<SiteUser> user = this.userRepository.findByUsername(username);
        if(user.isEmpty()) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
        return user.get();
    }

    public SiteUser findByNickname(String usernickname) {
        Optional<SiteUser> user = this.userRepository.findByNickname(usernickname);
        if(user.isEmpty()) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
        return user.get();
    }

    public SiteUser findById(Long id) {
        Optional<SiteUser> user = this.userRepository.findById(id);
        if(user.isEmpty()) {
            throw new RuntimeException("존재하지 않는 사용자입니다.");
        }
        return user.get();
    }

    // 회원 가입 시 검증
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

    // 프로필 수정 시 검증
    public BindingResult checkErrors(UserModifyForm userModifyForm, BindingResult bindingResult) {
        if(bindingResult.hasErrors()) {
            return bindingResult;
        }
        if(!userModifyForm.getPassword1().equals(userModifyForm.getPassword2())) {
            bindingResult.rejectValue("password2", "passwordInCorrect",
                    "2개의 패스워드가 일치하지 않습니다.");
            return bindingResult;
        }
        return bindingResult;
    }


    // ID 찾기 시, 회원 가입할 때 입력한 이메일 주소와 맞는지 지금 입력한 이메일 주소가 맞는지 검증
    public BindingResult checkEmailAndUser(EmailConfirmForm confirmForm, BindingResult bindingResult, String emailAddress) {
        if(bindingResult.hasErrors()) {
            return bindingResult;
        }
        if(!confirmForm.getEmail().equals(emailAddress)) {
            bindingResult.rejectValue("email", "passwordInCorrect",
                    "이메일 주소가 일치하지 않습니다.");
            return bindingResult;
        }
        return bindingResult;
    }


    // 이메일 인증번호 검증
    public BindingResult checkErrors(ConfirmNumberForm confirmForm, BindingResult bindingResult, int confirmNumber) {
        if(bindingResult.hasErrors()) {
            return bindingResult;
        }
        if(confirmForm.getConfirmNumber()!=confirmNumber) {
            bindingResult.rejectValue("confirmNumber", "passwordInCorrect",
                    "인증번호가 일치하지 않습니다.");
            return bindingResult;
        }
        return bindingResult;
    }

    public void removeSendMessage(List<Long> sendMessageList) {
        for ( int i = 0 ; i < sendMessageList.size(); i++) {
            SendMessage sendMessage = this.sendMessageRepository.findById(sendMessageList.get(i));
        }
    }


    @Transactional
    public SiteUser whenSocialLogin(String providerTypeCode, String username, String nickname) {

        // 소셜 로그인를 통한 가입시 비번은 없다.
        this.kakaoSignup(username, nickname);

        return findByUsername(username);
        // 최초 로그인 시 딱 한번 실행
    }
}


