package devcom.main.domain.teamInvite;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeamInviteForm {

    @NotNull(message = "팀 아이디가 없습니다.")
    private Long teamId;

    @NotEmpty(message = "회원명은 필수 입니다.")
    private String nickname;

}

