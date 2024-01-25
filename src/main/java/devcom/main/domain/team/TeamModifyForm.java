package devcom.main.domain.team;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class TeamModifyForm {

    @NotNull(message = "해당 게시글 아이디가 없어요")
    private Long id;

    @NotEmpty(message = "팀명은 필수 입니다.")
    private String name;

    @NotEmpty(message = "팀 설명은 필수 입니다.")
    private String description;
}
