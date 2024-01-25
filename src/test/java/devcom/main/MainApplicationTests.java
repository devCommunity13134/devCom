package devcom.main;

import devcom.main.domain.answer.Service.AnswerService;
import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.article.service.ArticleService;
import devcom.main.domain.skill.entity.Skill;
import devcom.main.domain.skill.service.SkillService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import devcom.main.domain.category.entity.Category;
import devcom.main.domain.category.service.CategoryService;
import devcom.main.domain.img.entity.Img;
import devcom.main.domain.img.service.ImgService;
import devcom.main.domain.reply.entity.Reply;
import devcom.main.domain.reply.service.ReplyService;
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

import java.security.Principal;

@SpringBootTest
class MainApplicationTests {

    @Autowired
    ArticleService articleService;
    @Autowired
    SkillService skillService;

    @Autowired
    UserService userService;
    @Autowired
    AnswerService answerService;
    @Autowired
    ReplyService replyService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ImgService imgService;


    @Test
    void contextLoads() {

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



//    @Test
//    void siteUserSignupTest(){
//        this.skillService.create(skills,);
//        this.userService.signup("user1","user1nick","1234","user1@gmail.com",'m',27,1000,"https://yt3.ggpht.com/lE7K-Ab6YHP4DzO9SwtFuS-GQxFbkklXcSCp99SFSzXWa6OlGw_esCFX0GUKuBa6DjaCd4-gmxsS=s800-c-fcrop64=1,2c330000d6ddffff-nd-v1","java", );
//    }

    // Category Test
    @Test
    void categoryCreateTest(){
        this.categoryService.create("front");
    }
    // Article Test
    @Test
    void articleCreateTest() {
        Category category = this.categoryService.getCategory("front");
        SiteUser author =  this.userService.findByUsername("user1");
        this.articleService.create(category, "[s8]", "Lorem ipsum dolor sit\n" +
                "                    amet, consectetur adipisicing elit. A ab asperiores, aspernatur at consectetur consequuntur cum\n" +
                "                    deleniti ea eius ipsum non nulla officiis pariatur perferendis quod reprehenderit, saepe tempora\n" +
                "                    tenetur. Lorem ipsum dolor sit amet, consectetur adipisicing elit. Consequuntur cum deleniti eaque\n" +
                "                    esse nulla numquam obcaecati praesentium quam sunt. Dolorem ducimus earum illo laudantium odio omnis\n" +
                "                    perferendis quae quaerat vel. Lorem ipsum dolor sit amet, consectetur adipisicing elit. A dolorum\n" +
                "                    est excepturi facere illo laborum, natus officia quasi reprehenderit voluptatem! Doloribus harum\n" +
                "                    officiis perspiciatis? Fugiat reprehenderit saepe sint totam voluptates! Lorem ipsum dolor sit amet,\n" +
                "                    consectetur adipisicing elit. Alias asperiores deleniti doloremque eum excepturi fuga inventore\n" +
                "                    ipsam iusto non obcaecati, possimus totam ullam veniam? Aliquam atque expedita illum qui vitae? ",author);
    }

    @Test
    void articleModfiyTest() {
        Article article = this.articleService.getArticle(1);
        this.articleService.modify(article, "s1Modify", "c1Modify");
    }

    @Test
    void articleDeleteTest() {
        Article article = this.articleService.getArticle(3);
        this.articleService.delete(article);
    }
    // Answer Test
    @Test
    void answerCreateTest() {
        Article article = this.articleService.getArticle(1);
        SiteUser author =  this.userService.findByUsername("user1");
        this.answerService.create(article, "answerContent1", author);
    }

    @Test
    void answerModfiyTest(){
        Answer answer = this.answerService.getAnswer(1);
        this.answerService.modify(answer,"answerContentModify");
    }

    @Test
    void answerDeleteTest(){
        Answer answer = this.answerService.getAnswer(3);
        this.answerService.delete(answer);
    }
    // Reply Test
    @Test
    void replyCreateTest(){
        Answer answer = this.answerService.getAnswer(1);
        SiteUser author =  this.userService.findByUsername("user1");
        this.replyService.create(answer,"reply1", author);
    }

    @Test
    void replyModfiyTest(){
        Reply reply = this.replyService.getReply(1);
        this.replyService.modify(reply,"replyContentModify");
    }

    @Test
    void replyDeleteTest(){
        Reply reply = this.replyService.getReply(1);
        this.replyService.delete(reply);
    }

    // Img test
    @Test
    void imgCreate() {
        this.imgService.create("https://yt3.ggpht.com/lE7K-Ab6YHP4DzO9SwtFuS-GQxFbkklXcSCp99SFSzXWa6OlGw_esCFX0GUKuBa6DjaCd4-gmxsS=s800-c-fcrop64=1,2c330000d6ddffff-nd-v1");
    }
    @Test
    void imgModify(){
        Img img = this.imgService.getImg(1);
        this.imgService.modify(img, "https://i.pinimg.com/564x/c0/6c/9f/c06c9fe99b3a364340f5d385c3ccc39c.jpg");
    }

    @Test
    void imgDelete(){
        Img img = this.imgService.getImg(1);
        this.imgService.delete(img);
    }

}
