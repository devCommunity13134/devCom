package devcom.main.domain.message.service;


import devcom.main.domain.message.entity.ReceiveMessage;
import devcom.main.domain.message.entity.SendMessage;
import devcom.main.domain.message.repository.ReceiveMessageRepository;
import devcom.main.domain.message.repository.SendMessageRepository;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SendMessageRepository sendMessageRepository;

    private final ReceiveMessageRepository receiveMessageRepository;

    private final UserService userService;


    public void addSendMessage(SiteUser sendUser, Long id, String content) {
        // 보낸 사람 sendUser
        // 받는 사람 user_id = id
        // 내용 content
        SendMessage sendMessage = SendMessage.builder()
                .user(sendUser)
                .content(content)
                .sendUserId(id)
                .build();

        this.sendMessageRepository.save(sendMessage);
        this.userService.findById(id).getSendMessageList().add(sendMessage);
    }

    public void addReceiveMessage(SiteUser receiveUser, Long id, String content) {
        // 받는 사람 receiveUser
        // 보낸 사람 user_id = id
        // 내용 content
        ReceiveMessage receiveMessage = ReceiveMessage.builder()
                .user(receiveUser)
                .content(content)
                .receiveUserId(id)
                .build();

        this.receiveMessageRepository.save(receiveMessage);
        this.userService.findById(id).getReceiveMessageList().add(receiveMessage);
    }
}
