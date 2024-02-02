package devcom.main.domain.user;


import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EmailConfirmForm {

    @NotEmpty(message = "이메일 주소를 입력해주세요.")
    private String email;

}