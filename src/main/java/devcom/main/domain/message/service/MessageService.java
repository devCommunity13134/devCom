package devcom.main.domain.message.service;


import devcom.main.domain.message.entity.ReceiveMessage;
import devcom.main.domain.message.entity.SendMessage;
import devcom.main.domain.message.repository.ReceiveMessageRepository;
import devcom.main.domain.message.repository.SendMessageRepository;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SendMessageRepository sendMessageRepository;

    private final ReceiveMessageRepository receiveMessageRepository;

    private final UserService userService;


    public void addSendMessage(SiteUser user, Long id, String content) {
        // 보낸 메세지 저장 → 작성한 user에만 저장
        // 보낸 사람 == user
        // 받는 사람 == user_id = id
        // 내용 content
        SendMessage sendMessage = SendMessage.builder()
                .user(user)
                .content(content)
                .sendUserId(id)
                .receiverName(this.userService.findById(id).getNickname())
                .build();

        this.sendMessageRepository.save(sendMessage);
        user.getSendMessageList().add(sendMessage);
    }

    public void addReceiveMessage(SiteUser user, Long id, String content) {
        // 받은 메세지 저장 → 상대방 user에 저장
        // 받는 사람 == user = findById(id)
        // id == 보낸 사람 user_id
        // 내용 content
        ReceiveMessage receiveMessage = ReceiveMessage.builder()
                .user(user)
                .content(content)
                .receiveUserId(id)
                .senderName(this.userService.findById(id).getNickname())
                .build();

        this.receiveMessageRepository.save(receiveMessage);
        user.getReceiveMessageList().add(receiveMessage);
    }

    public List<SiteUser> getSendUserList(SiteUser user) {
        List<SiteUser> sendUserList = new ArrayList<>();

        for(int i = 0; i < user.getSendMessageList().size(); i++) {
            sendUserList.add(this.userService.findById(user.getSendMessageList().get(i).getSendUserId()));
        }

        return sendUserList;
    }

    public List<SiteUser> getReceiveUserList(SiteUser user) {
        List<SiteUser> receiveUserList = new ArrayList<>();

        for(int i = 0; i < user.getReceiveMessageList().size(); i++) {
            receiveUserList.add(this.userService.findById(user.getReceiveMessageList().get(i).getReceiveUserId()));
        }

        return receiveUserList;
    }
}
