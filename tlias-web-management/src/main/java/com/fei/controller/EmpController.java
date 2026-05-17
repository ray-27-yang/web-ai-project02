package com.fei.controller;


import com.fei.pojo.Emp;
import com.fei.pojo.EmpQueryParam;
import com.fei.pojo.PageResult;
import com.fei.pojo.Result;
import com.fei.service.EmpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;

/**
 * 员工管理
 */
@RestController
@Slf4j
@RequestMapping("/emps")
public class EmpController {
    @Autowired
    private EmpService empService;
    /**
     * 分页查询
     */
    @GetMapping
    public Result page(EmpQueryParam empQueryParam){
        log.info("分页查询:{}",empQueryParam);
        PageResult<Emp> pageResult = empService.page(empQueryParam);
        return Result.success(pageResult);
    }
    /**
     * 新增员工
     */
    @PostMapping
    public Result save(@RequestBody Emp emp){
        log.info("新增员工：{},",emp);
        empService.save(emp);
        return Result.success();
    }
}
