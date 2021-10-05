package com.home.closematch.service;

import com.home.closematch.entity.Account;
import com.baomidou.mybatisplus.extension.service.IService;
import com.home.closematch.entity.vo.RegisterVo;
import com.home.closematch.entity.vo.UserBaseInfoVo;

/**
 *
 */
public interface AccountService extends IService<Account> {
    String login(Account account);

    /**
     * 登陆成功后 获取用户的name 权限等信息
     * @param accountId
     * @return
     */
    UserBaseInfoVo getUserBaseInfo();

    Account getAccountIgnoreIsDelete(Long accountId);

    void updateIsDeleteById(Long accountId, Integer val);

    void registerAccount(RegisterVo registerVo);

    void createAccount(String username, String password, Integer registerType);
}
