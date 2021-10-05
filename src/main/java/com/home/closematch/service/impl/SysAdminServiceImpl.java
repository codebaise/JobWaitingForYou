package com.home.closematch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.closematch.entity.Account;
import com.home.closematch.entity.SysAdmin;
import com.home.closematch.exception.ServiceErrorException;
import com.home.closematch.service.AccountService;
import com.home.closematch.service.SysAdminService;
import com.home.closematch.mapper.SysAdminMapper;
import com.home.closematch.utils.JwtUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

/**
 *
 */
@Service
public class SysAdminServiceImpl extends ServiceImpl<SysAdminMapper, SysAdmin>
implements SysAdminService{

    @Autowired
    private SysAdminMapper sysAdminMapper;
    @Autowired
    private AccountService accountService;

    @Override
    public String loginToBackStage(String username, String password) {
        SysAdmin sysAdmin = sysAdminMapper.selectOne(new QueryWrapper<SysAdmin>().eq("username", username));
        if (sysAdmin == null || !password.equals(sysAdmin.getPassword()))
            throw new ServiceErrorException(401, "用户名/密码错误");
        Map<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("userType", "admin");
        return JwtUtils.createJWT(claimsMap);
    }

    @Override
    public void changeUserAccountUserByAccountId(Long accountId) {
        Account account = accountService.getAccountIgnoreIsDelete(accountId);
        if (account == null)
            throw new ServiceErrorException(401, "Error Update Operation");
        accountService.updateIsDeleteById(accountId, account.getIsDelete() == 0 ? 1 : 0);
    }

}




