package com.seo2yeon.students.global.mail;

import com.sendgrid.*;
import com.sendgrid.helpers.mail.Mail;
import com.sendgrid.helpers.mail.objects.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    @Value("${sendgrid.api.key}")
    private String apiKey;

    @Value("${sendgrid.from.email}")
    private String fromEmail;

    public void sendVerificationEmail(String toEmail, String code) {

        Email from = new Email(fromEmail);
        Email to = new Email(toEmail);
        String subject = "[서이연 수학] 이메일 인증 안내";
        Content content = new Content(
                "text/plain",
                "안녕하세요.\n\n이메일 인증을 위해 아래 인증코드를 입력해주세요.\n\n인증코드: "
                        + code +
                        "\n\n3분 이내에 입력해주세요.\n\n감사합니다."
        );

        Mail mail = new Mail(from, subject, to, content);

        SendGrid sg = new SendGrid(apiKey);
        Request request = new Request();

        try {
            request.setMethod(Method.POST);
            request.setEndpoint("mail/send");
            request.setBody(mail.build());
            sg.api(request);
        } catch (Exception e) {
            throw new RuntimeException("메일 발송 실패", e);
        }
    }
}
