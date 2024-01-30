package devcom.main.domain.projectState;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectStateCreateForm {
    @NotEmpty(message = "해야할 내용은 필수 입니다.")
    private String content;

    @NotEmpty(message = "상태는 필수 입니다.")
    private String state;

    @NotNull(message = "프로젝트 아이디가 없습니다.")
    private Long projectId;
}
