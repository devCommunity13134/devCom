package devcom.main.domain.reply.service;

import devcom.main.domain.answer.entity.Answer;
import devcom.main.domain.article.entity.Article;
import devcom.main.domain.reply.ReplyForm;
import devcom.main.domain.reply.entity.Reply;
import devcom.main.domain.reply.repository.ReplyRepository;
import devcom.main.domain.user.entity.SiteUser;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ReplyService {
    private final ReplyRepository replyRepository;

    //reply create
    public void create(ReplyForm replyForm, Article originalArticle, Answer originalAnswer, SiteUser replyAuthor){
        Reply reply = Reply.builder()
                .originalArticle(originalArticle)
                .originalAnswer(originalAnswer)
                .content(replyForm.getContent())
                .author(replyAuthor)
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

    public void deleteByAuthorId(Long AuthorId){
        this.replyRepository.deleteByAuthorId(AuthorId);
    }

    public Reply getReply(long id){
        Optional<Reply> optionalReply = this.replyRepository.findById(id);
        if(optionalReply.isEmpty()){
            throw new RuntimeException();
        }
        return optionalReply.get();
    }

    public Page<Reply> getReplyList(int page, Article article){
        List<Sort.Order> sorts = new ArrayList<>();
        sorts.add(Sort.Order.asc("createDate"));
        Pageable pageable = PageRequest.of(page, 10, Sort.by(sorts));

        return this.replyRepository.findAllByOriginalArticle(article,pageable);
    }
}
