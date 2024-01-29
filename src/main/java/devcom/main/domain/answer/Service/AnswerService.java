package devcom.main.domain.answer.Service;

import devcom.main.domain.answer.Repository.AnswerRepository;
import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AnswerService {
    private final AnswerRepository answerRepository;

    //answer create
    public void create(Article originArticle, String content, SiteUser author){
        Answer answer = Answer.builder()
                .originalArticle(originArticle)
                .content(content)
                .author(author)
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
       this.answerRepository.delete(answer);
    }

    //deleteByAuthorId
    public void deleteByAuthorId(Long AuthorId){
        this.answerRepository.deleteByAuthorId(AuthorId);
    }

    public Answer getAnswer(long id){
        Optional<Answer> optionalAnswer = this.answerRepository.findById(id);
        if(optionalAnswer.isEmpty()){
            throw new RuntimeException();

        }
        return optionalAnswer.get();
    }
}
