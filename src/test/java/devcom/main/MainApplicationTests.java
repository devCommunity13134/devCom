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

import javax.mail.*;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.util.Properties;

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
}