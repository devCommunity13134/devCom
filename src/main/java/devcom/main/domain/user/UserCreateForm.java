package devcom.main.domain.user;


import devcom.main.domain.skill.entity.Skill;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class UserCreateForm {

    // String username, String password, String email, char sex, Integer age, Integer salary, String profileImg

    @NotEmpty(message = "ID는 필수항목 입니다.")
    private String username;

    @NotEmpty(message = "닉네임은 필수항목 입니다.")
    private String nickname;

    @NotEmpty(message = "비밀번호는 필수항목 입니다.")
    private String password1;

    @NotEmpty(message = "비밀번호 확인은 필수항목 입니다.")
    private String password2;

    @NotEmpty(message = "이메일은 필수항목 입니다.")
    @Email
    private String email;

    @NotNull(message = "전화번호는 필수항목 입니다.")
    private String phoneNumber;

    @NotNull(message = "성별은 필수항목 입니다.")
    private char sex;

    @NotNull(message = "나이는 필수항목 입니다.")
    private Integer age;

    private Integer salary;

    private String profileImg;

    private List<String> skill;

}
