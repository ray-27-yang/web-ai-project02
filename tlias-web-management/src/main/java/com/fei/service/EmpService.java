package com.fei.service;

import com.fei.pojo.Emp;
import com.fei.pojo.PageResult;

public interface EmpService {
    /**
     * 分页查询
     * @param page 页码
     * @param pageSize 每页查询的记录数
     */
    PageResult<Emp> page(Integer page, Integer pageSize);
}
