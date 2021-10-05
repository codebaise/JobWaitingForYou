package com.home.closematch;

import com.home.closematch.utils.MailUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;


@SpringBootTest
public class MailTest {
    @Autowired
    private MailUtils mailUtils;
    @Test
    public void sendSimpleMail() throws MessagingException {
//        MailUtils mailUtils = new MailUtils();
        mailUtils.sendAccountMail(MailUtils.MAIL_TYPE_Register, "123456", "973800739@qq.com");
    }
}

