package devcom.main.domain.user.entity;


import devcom.main.domain.skill.entity.Skill;
import devcom.main.global.jpa.BaseEntity;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.web.multipart.MultipartFile;

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
    private String nickname;

    @Column(columnDefinition = "varchar(100)")
    private String password;

    @Column(columnDefinition = "varchar(100)")
    private String email;

    @Column
    private String phoneNumber;

    @Column(length = 1)
    private char sex;

    @Column
    private Integer age;

    @Column
    private Integer salary;

    @Column(columnDefinition = "TEXT")
    private String profileImg;
    // 이미지 URL 문자열 저장

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<Skill> skillList;
}
