package devcom.main.domain.skill.entity;


import devcom.main.domain.user.entity.SiteUser;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class Skill {

    @ManyToOne
    private SiteUser user;

    private String skillName;

}
