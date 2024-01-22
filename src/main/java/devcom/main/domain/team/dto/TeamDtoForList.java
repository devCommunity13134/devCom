package devcom.main.domain.team.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Builder
@Getter
public class TeamDtoForList {

    private Long id;

    private String name;

    private String description;

    private String teamAdminName;

    private LocalDateTime createDate;
}
