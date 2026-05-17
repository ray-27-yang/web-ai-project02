package com.fei.service.impl;

import com.fei.mapper.EmpExprMapper;
import com.fei.mapper.EmpMapper;
import com.fei.pojo.Emp;
import com.fei.pojo.EmpExpr;
import com.fei.pojo.EmpQueryParam;
import com.fei.pojo.PageResult;
import com.fei.service.EmpService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.time.LocalDateTime;
import java.util.List;

/**
 * 同时操作员工基本信息和员工经历信息
 */
@Service
public class EmpServiceImpl implements EmpService {
    private final EmpMapper empMapper;
    private final EmpExprMapper empExprMapper;

    public EmpServiceImpl(EmpMapper empMapper, EmpExprMapper empExprMapper) {
        this.empExprMapper = empExprMapper;
        this.empMapper = empMapper;
    }

    @Override
    public PageResult<Emp> page(EmpQueryParam empQueryParam) {
        //1.设置分页参数
        PageHelper.startPage(empQueryParam.getPage(),empQueryParam.getPageSize());
        //2.执行查询
        List<Emp> list = empMapper.list(empQueryParam);
        //3.解析查询结果，并封装
        Page<Emp> p = (Page<Emp>)list;
        return new PageResult<Emp>(p.getTotal(),p.getResult());
    }

    @Override
    public void save(Emp emp) {
        //1.保存员工基本信息
        emp.setCreateTime(LocalDateTime.now());
        emp.setUpdateTime(LocalDateTime.now());
        empMapper.insert(emp);

        //2，保存员工工作经历信息
        List<EmpExpr> exprList = emp.getExprList();
        if (!CollectionUtils.isEmpty(exprList)){
            //遍历集合为empid赋值
            for (EmpExpr empExpr : exprList) {
                empExpr.setEmpId(emp.getId());
            }
            empExprMapper.insertBatch(exprList);
        }
    }
}
