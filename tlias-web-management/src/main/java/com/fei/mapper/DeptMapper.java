package com.fei.mapper;

import com.fei.pojo.Dept;
import org.apache.ibatis.annotations.*;

import java.util.List;


//自动创建一个实现类，并且交给IOC容器处理
@Mapper
public interface DeptMapper {
    /**
     *查询所有部门
     */
    /**
     * 方式一：手动结果映射
     */
//    @Results({
//        @Result(column = "create_time",property = "createTime"),
//        @Result(column = "update_time",property = "updateTime"),
//    })
    @Select("select id, name, create_time, update_time from dept order by update_time desc")
    List<Dept> findAll();

    /**
     * 根据id删除部门
     */
    @Delete("delete from dept where id = #{id}")
    void deleteById(Integer id);

    /**
     * 增加部门
     */
    @Insert("insert into dept(name,create_time,update_time) values (#{name},#{createTime},#{updateTime})")
    void add(Dept dept);

    /**
     * 根据id查询部门
     */
    @Select("select id, name, create_time, update_time from dept where id = #{id}")
    Dept getById(Integer id);

    /**
     * 根据id修改部门
     */
    @Update("update dept set name = #{name},update_time = #{updateTime} where id = #{id}")
    void update(Dept dept);
}
