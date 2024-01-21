package devcom.main;

import devcom.main.domain.answer.Service.AnswerService;
import devcom.main.domain.article.service.ArticleService;
import devcom.main.domain.img.service.ImgService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class MainApplicationTests {

	@Autowired
	ArticleService articleService;
	@Autowired
	AnswerService answerService;
	@Autowired
	ImgService imgService;

	@Test
	void contextLoads() {
		this.articleService.create("s1", "c1");
	}

	// img test
	// img create test
	@Test
	void imgCreate(){
		this.imgService.create("https://yt3.ggpht.com/lE7K-Ab6YHP4DzO9SwtFuS-GQxFbkklXcSCp99SFSzXWa6OlGw_esCFX0GUKuBa6DjaCd4-gmxsS=s800-c-fcrop64=1,2c330000d6ddffff-nd-v1");
	}

}