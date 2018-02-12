package com.kk.service.impl;

import com.kk.common.service.impl.BaseServiceImpl;
import com.kk.entity.testtable;
import com.kk.mapper.testtableMapper;
import com.kk.service.ITesttableService;
import org.springframework.stereotype.Service;

/**
 * Created by msi- on 2018/2/2.
 */
@Service
public class TesttableServiceImpl extends BaseServiceImpl<testtableMapper,testtable> implements ITesttableService {
}
