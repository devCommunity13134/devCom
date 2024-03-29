package devcom.main.domain.project;

import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectCreateForm {
    @NotEmpty(message = "프로젝트명은 필수 입니다.")
    private String name;

    @NotEmpty(message = "프로젝트 설명은 필수 입니다.")
    private String description;
}
