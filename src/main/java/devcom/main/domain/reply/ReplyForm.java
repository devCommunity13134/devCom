package devcom.main.domain.reply;

import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import jakarta.validation.constraints.NotEmpty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ReplyForm {

    @NotEmpty(message = "내용을 입력해주세요")
    private String content;

    @NotEmpty(message = "원 게시글 입력")
    private Article originalArticle;

    @NotEmpty(message = "원 댓글 입력")
    private Answer originaleAnswer;

}
