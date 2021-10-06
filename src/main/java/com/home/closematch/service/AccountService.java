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

    /**
     * 查询所有的用户, 直接使用mybatis-plus 获取的话, 会忽略已经被逻辑删除的记录
     */
    Account getAccountIgnoreIsDelete(Long accountId);

    void updateIsDeleteById(Long accountId, Integer val);

    /**
     * 根据填写的信息注册用户
     * @param registerVo
     */
    void registerAccount(RegisterVo registerVo);

    void createAccount(String username, String password, Integer registerType);
}
