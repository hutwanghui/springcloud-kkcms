package com.kk.api.service.impl;

import com.kk.api.entity.Moment;
import com.kk.api.entity.MomentItem;
import com.kk.api.mapper.MomentMapper;
import com.kk.api.service.IMomentService;
import com.kk.common.service.impl.BaseServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * Created by msi- on 2018/2/11.
 */
@Service
public class MomentServiceImpl extends BaseServiceImpl<MomentMapper, Moment> implements IMomentService {

    @Override
    public List<Moment> getMomentItem() {
        return null;
    }
}
