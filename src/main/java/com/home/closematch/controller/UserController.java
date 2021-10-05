package com.home.closematch.controller;

import com.home.closematch.entity.Account;
import com.home.closematch.entity.vo.EmailVo;
import com.home.closematch.entity.vo.RegisterVo;
import com.home.closematch.entity.vo.UserBaseInfoVo;
import com.home.closematch.pojo.Msg;
import com.home.closematch.service.AccountService;
import com.home.closematch.service.MailService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "用户注册登录相关Controller")
@RestController
public class UserController {

    @Autowired
    private AccountService accountService;
    @Autowired
    private MailService mailService;
    @ApiOperation("Login")
    @PostMapping("/login")
    public Msg login(@RequestBody Account account) {
        return Msg.success().add("token", accountService.login(account));
    }

    @ApiOperation("Register")
    @PostMapping("/register")
    public Msg register(@RequestBody @Validated RegisterVo registerVo){
        accountService.registerAccount(registerVo);
        return Msg.success().add("nextTo", "http://www.baidu.com");
    }

    @ApiOperation(value = "验证码发送", notes = "注册时申请发送验证码")
    @PostMapping("/mail/verifyCode")
    public Msg gerMailVerifyCode(@RequestBody @Validated EmailVo email) {
        mailService.sendRegisterMail(email.getEmail());
        return Msg.success();
    }

    @ApiOperation(value = "获取用户信息", notes = "登录成功后拉取用户信息")
    @GetMapping("/user/info")
    public Msg getUserBaseInfo() {
        UserBaseInfoVo userBaseInfo = accountService.getUserBaseInfo();
        return Msg.success().add("info", userBaseInfo);
    }
}
