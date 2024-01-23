package devcom.main;

import devcom.main.domain.article.service.ArticleService;
import devcom.main.domain.skill.entity.Skill;
import devcom.main.domain.skill.service.SkillService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
class MainApplicationTests {

    @Autowired
    ArticleService articleService;
    @Autowired
    SkillService skillService;

    @Autowired
    UserService userService;

    @Test
    void contextLoads() {

    }

    @Test
    void createSkills() {
        Skill skills1 = Skill.builder()
                .skillName("HTML")
                .build();
        Skill skills2 = Skill.builder()
                .skillName("CSS")
                .build();
        Skill skills3 = Skill.builder()
                .skillName("JS")
                .build();
        Skill skills4 = Skill.builder()
                .skillName("C")
                .build();
        Skill skills5 = Skill.builder()
                .skillName("C++")
                .build();
        Skill skills6 = Skill.builder()
                .skillName("Java")
                .build();
        Skill skills7 = Skill.builder()
                .skillName("Python")
                .build();
        Skill skills8 = Skill.builder()
                .skillName("SQL")
                .build();

        this.skillService.save(skills1);
        this.skillService.save(skills2);
        this.skillService.save(skills3);
        this.skillService.save(skills4);
        this.skillService.save(skills5);
        this.skillService.save(skills6);
        this.skillService.save(skills7);
        this.skillService.save(skills8);
    }

    @Test
    @Transactional
    void userSkillList() {
        SiteUser user = this.userService.findById(2L);
        if (user.getSkillList() == null) {
            System.out.println("======================skillList 저장안됨");
        }
        for(int i = 0; i < user.getSkillList().size(); i++) {
            System.out.println(user.getSkillList().get(i).getSkillName());
            System.out.println("test : "+i);
        }
    }


}