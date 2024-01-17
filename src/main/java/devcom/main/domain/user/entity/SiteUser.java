package devcom.main.domain.user.entity;


import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

@Entity
@Getter
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class SiteUser extends BaseEntity {

    private String username;

    private String password;

    private String email;

    private char sex;

    private Integer age;

    private Integer salary;

    private String authorization;

    private String profileImg;
}
