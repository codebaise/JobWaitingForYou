package com.home.closematch.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.home.closematch.entity.Account;
import com.home.closematch.entity.Message;
import com.home.closematch.entity.vo.NotifyMessageVo;
import com.home.closematch.pojo.Msg;
import com.home.closematch.service.MessageService;
import com.home.closematch.service.NotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Api(tags = "通知相关")
@RestController
public class NotifyController {
    @Autowired
    private NotifyService notifyService;
    @Autowired
    private MessageService messageService;

    // 默认状态为全局的广播
    @ApiOperation(value = "获取全局广播",
            notes = "登录用户行为")
    @GetMapping("/message")
    public Msg getMessages(@RequestParam(value = "page", defaultValue = "1")Integer page){
        return Msg.success().add("items", messageService.getGlobalMessage(page));
    }
    @ApiOperation(value = "主动添加广播",
            notes = "admin主动添加广播, 目前只能全局广播")
    @PostMapping("/message")
    public Msg addMessage(@RequestBody @Validated NotifyMessageVo notifyMessage){
        notifyService.sendGlobalMessage(notifyMessage);
        return Msg.success();
    }

    @ApiOperation(value = "根据不同的类型获取广播",
            notes = "其实是为了获取消息和私信开发的接口, 私信暂留" +
                    "目前写死, 只能获取remind消息")
    @GetMapping("/message/{messageType}")
    public Msg getMessageByType(@RequestParam(value = "page", defaultValue = "1")Integer page, @PathVariable("messageType") String messageType){
        // 埋坑 jwtString 应该可以直接从Spring security中获取 这里先使用这样获取
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Msg.success().add("items", messageService.getUserSelfMessage(page, account.getUserId(), account.getUserType()));
    }

    @GetMapping("/message/remind/count")
    public Msg getMessageNotReadCount() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return Msg.success().add("notReadMessageCount", messageService.getMessageNotReadCount(account.getUserId(), account.getUserType()));
    }
}
