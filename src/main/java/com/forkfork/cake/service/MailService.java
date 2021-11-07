package com.forkfork.cake.service;

import com.forkfork.cake.util.MailForm;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.UUID;

@Service
public class MailService {
    private final JavaMailSender javaMailSender;

    public MailService(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    public String sendCertificationCodeMail(String toEmail) throws MessagingException {

        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        int start = (int) (Math.random() * 27);
        String code = UUID.randomUUID().toString().replace("-", "").substring(start, start + 6);

        MailForm mailForm = new MailForm();
        String mailContent = mailForm.getCertificationCodeMail(code);

        helper.setFrom("Cake"); //보내는사람
        helper.setTo(toEmail); //받는사람
        helper.setSubject("[Cake] 회원가입 이메일 인증코드"); //메일제목
        helper.setText(mailContent, true); //ture넣을경우 html


        javaMailSender.send(mimeMessage);

        return code;
    }

    public String sendPwdCertification(String toEmail) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

        int start = (int) (Math.random() * 27);
        String code = UUID.randomUUID().toString().replace("-", "").substring(start, start + 6);

        MailForm mailForm = new MailForm();
        String mailContent = mailForm.getPwdCertificationMail(code);

        helper.setFrom("Cake"); //보내는사람
        helper.setTo(toEmail); //받는사람
        helper.setSubject("[Cake] 비밀번호 찾기 인증코드"); //메일제목
        helper.setText(mailContent, true); //ture넣을경우 html


        javaMailSender.send(mimeMessage);

        return code;
    }
}
