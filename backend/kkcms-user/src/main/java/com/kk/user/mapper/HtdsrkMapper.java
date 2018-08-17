package com.kk.user.mapper;

import com.kk.user.entity.Htdsrk;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

public interface HtdsrkMapper extends Mapper<Htdsrk> {
    public List<Htdsrk> getLv0();

    public List<Htdsrk> getLv1();

    public List<Htdsrk> getLv2();

    public List<Htdsrk> getLv3();

    public List<Htdsrk> getLv4();

    public List<Htdsrk> getLv5();
}