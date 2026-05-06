package com.fei.controller;

import com.fei.pojo.Dept;
import com.fei.pojo.Result;
import com.fei.service.DeptService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RequestMapping("/depts")
@RestController
public class DeptController {
    @Autowired
    private DeptService deptService;

    /**
     * 查询部门列表
     */
    @GetMapping
    public Result list(){
        log.info("查询所有部门顺序");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }
    /**
     * 删除部门
     */
    @DeleteMapping
    public Result delete(Integer id){
        log.info("根据id删除部门{}",id);
        deptService.deleteById(id);
        return Result.success();
    }
    /**
     * 增加部门
     */
    @PostMapping
    public Result add(@RequestBody Dept dept){
        log.info("新增部门{}",dept);
        deptService.add(dept);
        return Result.success();
    }

    /**
     * 根据id查询部门
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        log.info("根据id查询部门{}",id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 根据id修改部门
     */
    @PutMapping
    public Result change(@RequestBody Dept dept){
        log.info("修改部门{}",dept);
        deptService.update(dept);
        return Result.success();
    }
}
