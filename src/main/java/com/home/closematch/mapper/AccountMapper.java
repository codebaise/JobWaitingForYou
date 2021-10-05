package com.home.closematch.mapper;

import com.home.closematch.entity.Account;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @Entity com.home.closematch.entity.Account
 */
@Repository
public interface AccountMapper extends BaseMapper<Account> {
    Account selectAccountIgnoreIsDelete(Long accountId);

    int updateIsDeleteById(@Param("accountId") Long accountId, @Param("val") Integer val);
}




