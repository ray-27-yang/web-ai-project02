package com.fei.service;

import com.fei.pojo.Dept;

import java.util.List;

public interface DeptService {
    /**
     * 查询所有部门信息
     */
    List<Dept> findAll();

    /**
     * 根据id删除部门
     */
    void deleteById(Integer id);
    /**
     * 增加部门
     */
    void add(Dept dept);

    /**
     * 根据id查询部门
     */
    Dept getById(Integer id);

    /**
     * 根据id修改部门
     */
    void update(Dept dept);
}
