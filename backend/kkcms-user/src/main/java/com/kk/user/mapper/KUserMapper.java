package com.kk.user.mapper;

import com.kk.user.entity.KUser;
import org.apache.ibatis.annotations.Param;
import org.springframework.security.access.method.P;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

public interface KUserMapper extends Mapper<KUser> {
    KUser getByUsername(@Param("userName") String userName);

    List<KUser> getByKey(@Param("kUser") KUser kUser);

    List<KUser> getByEmail(@Param("email") String email);

    KUser getByMobilePhone(@Param("mobile") String mobile);

}