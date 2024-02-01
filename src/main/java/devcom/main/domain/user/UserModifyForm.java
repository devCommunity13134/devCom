package devcom.main.domain.user;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class UserModifyForm {

    // String username, String password, String email, char sex, Integer age, Integer salary, String profileImg

    @NotEmpty(message = "닉네임은 필수항목 입니다.")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수항목 입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목 입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목 입니다.")
    @Email
    private String email;

    @NotEmpty(message = "전화번호는 필수항목 입니다.")
    @Pattern(regexp = "^01([0|1|6|7|8|9])-?([0-9]{3,4})-?([0-9]{4})$", message = "전화번호는 '010-XXXX-XXXX' 또는 '010XXXXXXXX' 형식만 입력 가능합니다.")
    private String phoneNumber;

    private Integer salary;

    private MultipartFile profileImg;

    private List<String> skill;

}
