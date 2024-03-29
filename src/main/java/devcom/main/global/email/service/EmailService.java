package devcom.main.global.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;



@Service
@RequiredArgsConstructor
public class EmailService {
    private final JavaMailSender mailSender;

    public void send(String to, String subject, String body) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(to); // 메일 수신자
            mimeMessageHelper.setSubject(subject); // 메일 제목
            mimeMessageHelper.setText(body, true); // 메일 본문 내용, HTML 여부
            mailSender.send(mimeMessage); // 메일방송
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

    }

    public int sendConfirm(String to) {

        MimeMessage mimeMessage = mailSender.createMimeMessage();

        int randomNumber = (int) (Math.random()*10000);
        String subject = "[devCom] ID찾기 인증번호입니다.";
        String content = "인증번호 [" + randomNumber + "] 를 입력해주세요.";

        try {
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage, false, "UTF-8");
            mimeMessageHelper.setTo(to); // 메일 수신자
            mimeMessageHelper.setSubject(subject); // 메일 제목
            mimeMessageHelper.setText(content, true); // 메일 본문 내용, HTML 여부
            mailSender.send(mimeMessage); // 메일방송
        } catch (MessagingException e) {
            throw new RuntimeException(e);
        }

        return randomNumber;

    }
}