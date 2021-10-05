package com.home.closematch.service;

import com.home.closematch.entity.SysAdmin;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 *
 */
public interface SysAdminService extends IService<SysAdmin> {

    String loginToBackStage(String username, String password);

    void changeUserAccountUserByAccountId(Long accountId);

}
