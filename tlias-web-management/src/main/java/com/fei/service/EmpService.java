package com.fei.service;

import com.fei.pojo.Emp;
import com.fei.pojo.EmpQueryParam;
import com.fei.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

public interface EmpService {
    /**
     * 分页查询
     */
    PageResult<Emp> page(EmpQueryParam empQueryParam);
}
