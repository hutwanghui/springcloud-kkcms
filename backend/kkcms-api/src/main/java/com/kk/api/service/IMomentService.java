package com.kk.api.service;

import com.kk.api.entity.Moment;
import com.kk.api.entity.MomentItem;
import com.kk.common.service.BaseService;

import java.util.List;

/**
 * Created by msi- on 2018/2/11.
 */
public interface IMomentService extends BaseService<Moment> {
    public List<Moment> getMomentItem();

}
