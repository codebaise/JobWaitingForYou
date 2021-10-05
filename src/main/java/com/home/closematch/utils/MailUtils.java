package com.home.closematch.utils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Component;
import org.thymeleaf.TemplateEngine;
import org.thymeleaf.context.Context;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.util.Date;

@Component
public class MailUtils {
    public static final String MAIL_TYPE_Register = "注册";
    public static final String MAIL_TYPE_RecallPwd = "找回密码";

    private JavaMailSender javaMailSender;
    private TemplateEngine templateEngine;
    private ThreadPoolTaskExecutor taskExecutor;

    @Value("${spring.mail.username}")
    private String sendFrom;

    @Autowired
    private void setJavaMailSender(JavaMailSender javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Autowired
    private void setTemplateEngine(TemplateEngine templateEngine) {
        this.templateEngine = templateEngine;
    }

    @Autowired
    private void setThreadPoolTaskExecutor(ThreadPoolTaskExecutor taskExecutor) {
        this.taskExecutor = taskExecutor;
    }

    public void sendMail(String content, String... sendTo) {
        try {
            MimeMessage mimeMessage = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
            helper.setSubject("Websit Register");
            helper.setFrom(sendFrom);
            helper.setTo(sendTo);
            helper.setCc("71120450@qq.com");
            helper.setBcc("71120450@qq.com");
            helper.setSentDate(new Date());
            Context context = new Context();
            context.setVariable("mailType", "test");
            context.setVariable("codeVal","000001");
            String process = templateEngine.process("account_mail.html", context);
            helper.setText(process,true);
            javaMailSender.send(mimeMessage);
        } catch (MessagingException e){
            System.out.println(e.getMessage());
        }

    }

    public void sendAccountMail(String mailType, String codeVal, String... sendTo) {
        taskExecutor.execute(() -> {
            try {
                MimeMessage mimeMessage = javaMailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
                helper.setSubject("账户邮件");
                helper.setFrom(sendFrom);
                helper.setTo(sendTo);
                helper.setCc(sendFrom);
                helper.setBcc(sendFrom);
                helper.setSentDate(new Date());
                Context context = new Context();
                context.setVariable("mailType", mailType);
                context.setVariable("codeVal",codeVal);
                String process = templateEngine.process("account_mail.html", context);
                helper.setText(process,true);
                javaMailSender.send(mimeMessage);
            } catch (MessagingException e){
                System.out.println(e.getMessage());
            }

        });

    }

}
