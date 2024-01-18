package devcom.main.domain.user.entity;


import devcom.main.domain.skill.entity.Skill;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Getter
@SuperBuilder(toBuilder = true)
@NoArgsConstructor
@AllArgsConstructor
public class SiteUser extends BaseEntity {
    //    상속
    //    private Long id;
    //    private LocalDateTime createDate;
    //    private LocalDateTime modifiedDate;

    @Column(unique = true)
    private String username;

    @Column(columnDefinition = "varchar(100)")
    private String password;

    @Column(columnDefinition = "varchar(100)")
    private String email;

    @Column(length = 1)
    private char sex;
    // '남' , '여'

    @Column
    private Integer age;


    @Column
    private Integer salary;


    @Column
    private String authorization;
    // 회원 권한 (일반, 관리자)


    @Column(columnDefinition = "varchar(255)")
    private String profileImg;
    // 이미지 URL 문자열 저장


    @OneToMany(mappedBy = "skill", cascade = CascadeType.REMOVE)
    private List<Skill> skillList;

}
