package com.home.closematch.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.home.closematch.entity.Account;
import com.home.closematch.entity.Humanresoucres;
import com.home.closematch.entity.JobSeeker;
import com.home.closematch.entity.vo.RegisterVo;
import com.home.closematch.entity.vo.UserBaseInfoVo;
import com.home.closematch.exception.ServiceErrorException;
import com.home.closematch.service.AccountService;
import com.home.closematch.mapper.AccountMapper;
import com.home.closematch.service.HumanresoucresService;
import com.home.closematch.service.JobSeekerService;
import com.home.closematch.utils.AccountUtils;
import com.home.closematch.utils.JwtUtils;
import com.home.closematch.utils.VerifyUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;

/**
 *
 */
@Service
public class AccountServiceImpl extends ServiceImpl<AccountMapper, Account>
implements AccountService, UserDetailsService {

    @Autowired
    private AccountMapper accountMapper;


    @Autowired
    private JobSeekerService jobSeekerService;
    @Autowired
    private HumanresoucresService humanresoucresService;

    @Override
    public Account loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountMapper.selectOne(new QueryWrapper<Account>().eq("username", username));
        if (account == null) {
            throw new UsernameNotFoundException("用户名不存在!");
        }
        String role = "";
        if (account.getUserId() != null && account.getUserId() == 0 && account.getUserType().equals(AccountUtils.USER_TYPE_ADMIN))
            role = "admin";
        else
            role = account.getUserType().equals(AccountUtils.USER_TYPE_SEEKER) ? "seeker" : "hr";
        account.setRole(role);
        return account;
    }

    @Override
    public String login(Account account) {
        Account accountObj = accountMapper.selectOne(new QueryWrapper<Account>().eq("username", account.getUsername()));
        if (accountObj == null || !account.getPassword().equals(accountObj.getPassword()))
            throw new ServiceErrorException(401, "用户名/密码错误");
        HashMap<String, Object> claimsMap = new HashMap<>();
        claimsMap.put("userId", accountObj.getId());
        // 0: Seeker, 1: Hr
        claimsMap.put("userType", accountObj.getUserType());

        return JwtUtils.createJWT(claimsMap);
    }

    /**
     * 登陆成功后 获取用户的name 权限等信息
     * homeLink
     */
    @Override
    public UserBaseInfoVo getUserBaseInfo() {
        Account account = (Account) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        List<String> roles = AccountUtils.getRoles(account.getUserType());
        String homeLink = AccountUtils.getHomeLink(account.getUserType());
        // 创建用户的同时已经创建过了, 所以不是null
//        if (account.getUserId() == null)
//            return new UserBaseInfoVo(null, account.getUserType(), roles, 0, homeLink);
        Integer infoFillLevel = 2;
        // 通过用户类型进行区分
        // seeker
        if (account.getUserType().equals(AccountUtils.USER_TYPE_SEEKER)){
            JobSeeker jobSeeker = jobSeekerService.getById(account.getUserId());
            infoFillLevel = inspectSeekerInfoFillLevel(jobSeeker);
            return new UserBaseInfoVo(jobSeeker.getName(), account.getUserType(), roles, infoFillLevel, homeLink);
        } else if (account.getUserType().equals(AccountUtils.USER_TYPE_HR)){
            // hr
            Humanresoucres hr = humanresoucresService.getById(account.getUserId());
            infoFillLevel = inspectHrInfoFillLevel(hr);
            return new UserBaseInfoVo(hr.getName(), account.getUserType(), roles, infoFillLevel, homeLink);
        } else if (account.getUserType().equals(AccountUtils.USER_TYPE_ADMIN)) {
            // admin
            return new UserBaseInfoVo("ADMIN_YOU", account.getUserType(), roles, AccountUtils.USER_INFO_FILL_LEVEL_HR_SUCCESS, homeLink);
        } else
            throw new ServiceErrorException(400, "未知错误");
    }

    /**
     * 用户名信息
     * 必填信息有学校
     * @param jobSeeker
     * @return
     */
    private Integer inspectSeekerInfoFillLevel(JobSeeker jobSeeker) {
        return 2;
    }

    private Integer inspectHrInfoFillLevel(Humanresoucres humanresoucres) {
        if(humanresoucres.getName() == null)
            return AccountUtils.USER_INFO_FILL_LEVEL_NO_BASE_INFO;
        if (humanresoucres.getCompanyId() == null || humanresoucres.getCompanyId() == 0)
            return AccountUtils.USER_INFO_FILL_LEVEL_NO_COMPANY;
        else
            return AccountUtils.USER_INFO_FILL_LEVEL_HR_SUCCESS;
    }

    @Override
    public Account getAccountIgnoreIsDelete(Long accountId) {
        return accountMapper.selectAccountIgnoreIsDelete(accountId);
    }

    @Override
    public void updateIsDeleteById(Long accountId, Integer val) {
        accountMapper.updateIsDeleteById(accountId, val);
    }

    @Override
    public void registerAccount(RegisterVo registerVo) {
        boolean inspectResult = VerifyUtils.inspectCode(registerVo.getEmail(), registerVo.getVerifyCode());
        if (!inspectResult)
            throw new ServiceErrorException(403, "验证码错误");

        Account account = accountMapper.selectOne(new QueryWrapper<Account>().eq("username", registerVo.getEmail()));
        if (account != null)
            throw new ServiceErrorException(400, "用户名已存在");
        createAccount(registerVo.getEmail(), registerVo.getPassword(), registerVo.getRegisterType());
    }

    @Override
    public void createAccount(String username, String password, Integer registerType) {
        Long userId = null;
        if (registerType.equals(AccountUtils.USER_TYPE_SEEKER)){
            JobSeeker jobSeeker = new JobSeeker();
            jobSeekerService.save(jobSeeker);
            userId = jobSeeker.getId();
        } else {
            Humanresoucres humanresoucres = new Humanresoucres();
            humanresoucresService.save(humanresoucres);
            userId = humanresoucres.getId();
        }
        Account account = new Account();
        account.setUsername(username);
        account.setPasswordUseEncode(password);
        account.setUserType(registerType);
        account.setUserId(userId);
        accountMapper.insert(account);
    }
}




