package devcom.main.domain.user;


import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ConfirmForm {

    @NotNull(message = "인증번호를 입력해주세요.")
    private Integer confirmNum;

}
