package devcom.main.domain.project;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ProjectModifyForm {

    @NotNull(message = "유효하지 않은 접근 입니다.")
    private Long id;

    @NotEmpty(message = "프로젝트명은 필수 입니다.")
    private String name;

    @NotEmpty(message = "프로젝트 설명은 필수 입니다.")
    private String description;
}
