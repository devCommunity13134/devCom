package devcom.main;

import devcom.main.domain.answer.Service.AnswerService;
import devcom.main.domain.article.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainApplicationTests {

	@Autowired
	ArticleService articleService;
	@Autowired
	AnswerService answerService;
	@Test
	void contextLoads() {
		this.articleService.create("s1", "c1");
	}

}