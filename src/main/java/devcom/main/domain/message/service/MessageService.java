package devcom.main.domain.message.service;


import devcom.main.domain.message.entity.ReceiveMessage;
import devcom.main.domain.message.entity.SendMessage;
import devcom.main.domain.message.repository.ReceiveMessageRepository;
import devcom.main.domain.message.repository.SendMessageRepository;
import devcom.main.domain.team.service.TeamAndProjectService;
import devcom.main.domain.teamInvite.entity.TeamInvite;
import devcom.main.domain.teamInvite.service.TeamInviteService;
import devcom.main.domain.user.entity.SiteUser;
import devcom.main.domain.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MessageService {

    private final SendMessageRepository sendMessageRepository;
    private final TeamInviteService teamInviteService;
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
                .checked(false)
                .build();

        this.sendMessageRepository.save(sendMessage);
        user.getSendMessageList().add(sendMessage);
    }

    public ReceiveMessage addReceiveMessage(SiteUser user, Long id, String content) {
        // 받은 메세지 저장 → 상대방 user에 저장
        // 받는 사람 == user = findById(id)
        // id == 보낸 사람 user_id
        // 내용 content
        ReceiveMessage receiveMessage = ReceiveMessage.builder()
                .user(user)
                .content(content)
                .receiveUserId(id)
                .senderName(this.userService.findById(id).getNickname())
                .checked(false)
                .build();

        this.receiveMessageRepository.save(receiveMessage);
        user.getReceiveMessageList().add(receiveMessage);

        return receiveMessage;
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

    public SendMessage findSmById(String id) {
        Optional<SendMessage> sendMessage = this.sendMessageRepository.findById((long) Integer.parseInt(id));
        if(sendMessage.isEmpty()) {
            throw new RuntimeException("존재하지 않는 메세지입니다.");
        }
        return sendMessage.get();
    }

    public ReceiveMessage findRmById(String id) {
        Optional<ReceiveMessage> receiveMessage = this.receiveMessageRepository.findById((long) Integer.parseInt(id));
        if(receiveMessage.isEmpty()) {
            throw new RuntimeException("존재하지 않는 메세지입니다.");
        }
        return receiveMessage.get();
    }

    public void removeSendMessage(List<String> messageIdList) {
        for (int i = 0; i < messageIdList.size(); i++) {
            SendMessage sendMessage = this.findSmById(messageIdList.get(i));
            this.sendMessageRepository.delete(sendMessage);
        }
    }

    public void removeReceiveMessage(List<String> messageIdList) {
        for (int i = 0; i < messageIdList.size(); i++) {
            ReceiveMessage receiveMessage = this.findRmById(messageIdList.get(i));
            Optional<TeamInvite> ti = teamInviteService.getTeamInviteByReceiveMessage(receiveMessage);
            if(ti.isPresent()){
                this.teamInviteService.delete(ti.get());
                continue;
            }
            this.receiveMessageRepository.delete(receiveMessage);
        }
    }

    public void receiveMessageChecked(ReceiveMessage receiveMessage) {
        ReceiveMessage rm = receiveMessage.toBuilder()
                .checked(true)
                .build();

        this.receiveMessageRepository.save(rm);
    }

}
