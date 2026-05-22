package com.fei.mapper;


import com.fei.pojo.Emp;
import com.fei.pojo.EmpQueryParam;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.time.LocalDate;
import java.util.List;

/**
 * 操作员工信息
 */
@Mapper
public interface EmpMapper {
    //@Select("select e.*,d.name as deptName from emp e left join dept d on e.dept_id = d.id order by e.update_time desc")

    /**
     * 条件查询员工信息
     */
    public List<Emp> list(EmpQueryParam empQueryParam);

    /**
     * 新增员工基本信息#{属性名}
     */
    //获取生成的主键-主键返回
    @Options(useGeneratedKeys = true,keyProperty = "id")
    @Insert("insert into emp(username, name, gender, phone, job, salary, image, entry_date, dept_id, create_time, update_time)" +
            "values (#{username},#{name},#{gender},#{phone},#{job},#{salary},#{image},#{entryDate},#{deptId},#{createTime},#{updateTime});")
    void insert(Emp emp);

    /**
     * 根据id批量删除员工基本信息
     */
    void deleteByIds(List<Integer> ids);


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
