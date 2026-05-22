package com.fei.service;

import com.fei.pojo.Emp;
import com.fei.pojo.EmpQueryParam;
import com.fei.pojo.PageResult;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;
import java.util.List;

public interface EmpService {
    /**
     * 分页查询
     */
    PageResult<Emp> page(EmpQueryParam empQueryParam);

    /**
     * 新增员工
     */
    void save(Emp emp);

    /**
     * 删除员工
     */
    void delete(List<Integer> ids);
}
