package devcom.main.domain.user;


import devcom.main.domain.skill.entity.Skill;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class UserCreateForm {

    @NotEmpty
    private String username;

    @NotEmpty
    private String password;

    @NotEmpty
    private String email;

    @NotEmpty
    private char sex;
    // '남' , '여'

    @NotEmpty
    private Integer age;


    @NotEmpty
    private Integer salary;


    @NotEmpty
    private String authorization;
    // 회원 권한 (일반, 관리자)

    @NotEmpty
    private String profileImg;
    // 이미지 URL 문자열 저장

    @NotEmpty
    private List<Skill> skillList;


}
