package devcom.main.domain.team;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeamCreateForm {

    @NotEmpty(message = "팀명은 필수 입니다.")
    private String name;

    @NotEmpty(message = "팀 설명은 필수 입니다.")
    private String description;
}
