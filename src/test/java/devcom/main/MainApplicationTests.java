package devcom.main;

import devcom.main.domain.answer.Service.AnswerService;
import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.article.service.ArticleService;
import devcom.main.domain.category.entity.Category;
import devcom.main.domain.category.service.CategoryService;
import devcom.main.domain.img.service.ImgService;
import devcom.main.domain.reply.service.ReplyService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.security.Principal;

@SpringBootTest
class MainApplicationTests {

    @Autowired
    ArticleService articleService;
    @Autowired
    AnswerService answerService;
    @Autowired
    ReplyService replyService;
    @Autowired
    CategoryService categoryService;
    @Autowired
    ImgService imgService;
    @Autowired
    UserService userService;

    // SiteUser Test
    // create "user1"
    @Test
    void siteUserSignupTest(){
        this.userService.signup("user1","user1nick","1234","user1@gmail.com",'m',27,1000,"https://yt3.ggpht.com/lE7K-Ab6YHP4DzO9SwtFuS-GQxFbkklXcSCp99SFSzXWa6OlGw_esCFX0GUKuBa6DjaCd4-gmxsS=s800-c-fcrop64=1,2c330000d6ddffff-nd-v1","java");
    }

    // Category Test
    @Test
    void categoryCreateTest(){
        this.categoryService.create("front");
    }
    // Article Test
    @Test
    void articleCreateTest() {
        Category category = this.categoryService.getCategory("front");
        SiteUser author =  this.userService.findByusername("user1");
        this.articleService.create(category, "s2", "c2",author);
    }

    @Test
    void articleModfiyTest() {
        Article article = this.articleService.getArticle(2);
        this.articleService.modify(article, "s2Modify", "c2Modify");
    }

    @Test
    void articleDeleteTest() {
        Article article = this.articleService.getArticle(3);
        this.articleService.delete(article);
    }
    // Answer Test
    @Test
    void answerCreateTest() {
        Article article = this.articleService.getArticle(4);
        SiteUser author =  this.userService.findByusername("user1");
        this.answerService.create(article, "answerContent1", author);
    }

    @Test
    void answerModfiyTest(){
        Answer answer = this.answerService.getAnswer(3);
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
        Answer answer = this.answerService.getAnswer(2);
        SiteUser author =  this.userService.findByusername("user1");
        this.replyService.create(answer,"reply1", author);
    }

    // Img test
    @Test
    void imgCreate() {
        this.imgService.create("https://yt3.ggpht.com/lE7K-Ab6YHP4DzO9SwtFuS-GQxFbkklXcSCp99SFSzXWa6OlGw_esCFX0GUKuBa6DjaCd4-gmxsS=s800-c-fcrop64=1,2c330000d6ddffff-nd-v1");
    }


}
