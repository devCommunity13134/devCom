package devcom.main.domain.reply.service;

import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.reply.entity.Reply;
import devcom.main.domain.reply.repository.ReplyRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    //reply create
    public void create(Answer originAnswer, String content){
        Reply reply = Reply.builder()
                .originalAnswer(originAnswer)
                .content(content)
                .build();
        this.replyRepository.save(reply);
    }

    //reply modify
    public void modify(Reply reply, String content){
        Reply reply1 = reply.toBuilder()
                .content(content)
                .build();

        this.replyRepository.save(reply1);
    }

    //reply delete
    public void delete(Reply reply){
        // need delete reply
        this.replyRepository.delete(reply);
    }
}
