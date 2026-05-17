package com.fei.mapper;


import com.fei.pojo.Emp;
import com.fei.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * 操作员工信息
 */
@Mapper
public interface EmpMapper {
    //@Select("select e.*,d.name as deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time desc")
    public List<Emp> list(EmpQueryParam empQueryParam);
    // ------------------------ 原始分页查询-----------------------------------
    /**
     * 查询总记录数
     */
//    @Select("select count(*) from emp e left join dept d on e.dept_id = d.id")
//    public long count();
    /**
     * 分页查询
     */
//    @Select("select e.*,d.name as deptName from emp e left join dept d " +
//            "on e.dept_id = d.id order by e.update_time desc limit #{start},#{pageSize}")
//    public List<Emp> list(Integer start,Integer pageSize);


}
