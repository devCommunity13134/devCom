package devcom.main.domain.article;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ArticleForm {
    @NotEmpty(message = "제목을 작성해주세요")
    private String subject;

    @NotEmpty(message = "내용을 작성해주세요")
    private String content;

    @NotEmpty(message = "카테고리를 선택해주세요")
    private String categoryName;

}
