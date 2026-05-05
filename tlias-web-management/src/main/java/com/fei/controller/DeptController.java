package com.fei.controller;

import com.fei.pojo.Dept;
import com.fei.pojo.Result;
import com.fei.service.DeptService;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
        System.out.println("查询所有部门顺序");
        List<Dept> deptList = deptService.findAll();
        return Result.success(deptList);
    }
    /**
     * 删除部门
     */
    @DeleteMapping
    public Result delete(Integer id){
        System.out.println("删除id为" + id + "的部门");
        deptService.deleteById(id);
        return Result.success();
    }
    /**
     * 增加部门
     */
    @PostMapping
    public Result add(@RequestBody Dept dept){
        System.out.println("新增部门" + dept);
        deptService.add(dept);
        return Result.success();
    }

    /**
     * 根据id查询部门
     */
    @GetMapping("/{id}")
    public Result getInfo(@PathVariable Integer id){
        System.out.println("根据id查询部门" + id);
        Dept dept = deptService.getById(id);
        return Result.success(dept);
    }

    /**
     * 根据id修改部门
     */
    @PutMapping
    public Result change(@RequestBody Dept dept){
        System.out.println("修改部门" + dept);
        deptService.update(dept);
        return Result.success();
    }
}
