package com.fei.service.impl;

import com.fei.mapper.EmpMapper;
import com.fei.pojo.Emp;
import com.fei.pojo.PageResult;
import com.fei.service.EmpService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 同时操作员工基本信息和员工经历信息
 */
@Service
public class EmpServiceImpl implements EmpService {
    @Autowired
    private EmpMapper empMapper;
    @Override
    public PageResult<Emp> page(Integer page, Integer pageSize) {
        //1.调用Mapper接口查询总记录数
        long total = empMapper.count();
        //2.调用Mapper接口查询结果列表
        Integer start = (page - 1)* pageSize;
        List<Emp> rows = empMapper.list(start,pageSize);
        //3.封装结果
        return new PageResult<Emp>(total,rows);
    }
}
