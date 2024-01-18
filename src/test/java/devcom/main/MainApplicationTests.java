package devcom.main;

import devcom.main.domain.article.service.ArticleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainApplicationTests {

	@Autowired
	ArticleService articleService;

	@Test
	void contextLoads() {
		this.articleService.create("제목","내용");
	}

}