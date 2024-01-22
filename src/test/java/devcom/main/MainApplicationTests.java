package devcom.main;

import devcom.main.domain.article.service.ArticleService;
import devcom.main.domain.skill.entity.Skill;
import devcom.main.domain.skill.service.SkillService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainApplicationTests {

    @Autowired
    ArticleService articleService;
    @Autowired
    SkillService skillService;

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


}