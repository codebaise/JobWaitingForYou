package com.home.closematch.service.impl;

import com.home.closematch.service.MailService;
import com.home.closematch.utils.MailUtils;
import com.home.closematch.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailServiceImpl implements MailService {
    @Autowired
    private MailUtils mailUtils;

    @Override
    public void sendRegisterMail(String email) {
        String s = VerifyUtils.generateVerifyCode();
        VerifyUtils.verifyCodeMap.put(email.trim(), s);
        mailUtils.sendAccountMail(MailUtils.MAIL_TYPE_Register, s, email);
    }
}
