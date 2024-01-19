package devcom.main.domain.answer.Service;

import devcom.main.domain.answer.Repository.AnswerRepository;
import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    //answer create
    public void create(Article originArticle, String content){
        Answer answer = Answer.builder()
                .originalArticle(originArticle)
                .content(content)
                .build();
        this.answerRepository.save(answer);
    }

    //answer modify
    public void modify(Answer answer, String content){
        Answer answer1 = answer.toBuilder()
                .content(content)
                .build();

        this.answerRepository.save(answer1);
    }

    //answer delete
    public void delete(Answer answer){
        // need delete reply
       this.answerRepository.delete(answer);
    }


}
