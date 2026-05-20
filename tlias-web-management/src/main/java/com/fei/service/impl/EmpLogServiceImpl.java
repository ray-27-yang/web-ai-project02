package com.fei.service.impl;

import com.fei.mapper.EmpLogMapper;
import com.fei.pojo.EmpLog;
import com.fei.service.EmpLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public class EmpLogServiceImpl implements EmpLogService {

    @Autowired
    private EmpLogMapper empLogMapper;
    @Transactional(propagation = Propagation.REQUIRES_NEW)//在一个新的事务中运行
    @Override
    public void insertLog(EmpLog empLog) {
        empLogMapper.insert(empLog);
    }
}
