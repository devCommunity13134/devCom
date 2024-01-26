package devcom.main.domain.projectState.entity;

import devcom.main.domain.project.entity.Project;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotEmpty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class ProjectState extends BaseEntity {

    @NotEmpty(message = "해야할 내용은 필수 입니다.")
    private String content;

    @NotEmpty(message = "상태는 필수 입니다.")
    private String state;

    @ManyToOne
    private Project project;
}
